package com.tiaoin.crawl.plugin.duplicate;

import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;
import com.tiaoin.crawl.common.utils.FileUtil;
import com.tiaoin.crawl.common.utils.StringUtil;
import com.tiaoin.crawl.core.spider.Settings;

public class DocIDServer {

    private String       name      = null;
    public Environment   env       = null;
    public Database      db        = null;
    private final Object mutex     = new Object();
    private int          lastDocID = 0;

    public DocIDServer(String name) {
        this.name = name;
        File _dbEnv = new File(Settings.website_visited_folder());
        if (!_dbEnv.exists()) {
            String error = "dbEnv folder -> " + _dbEnv.getAbsolutePath() + " not found !";
            RuntimeException e = new RuntimeException(error);
            //listener.onError(Thread.currentThread(), null, error, e);
            throw e;
        }
        File dir = new File(_dbEnv.getAbsolutePath() + "/" + name);
        if (!dir.exists())
            dir.mkdir();

        for (File f : dir.listFiles()) {
            boolean flag = FileUtil.deleteFile(f);
            if (!flag) {
                String error = "file -> " + f.getAbsolutePath() + " can not delete !";
                RuntimeException e = new RuntimeException(error);
                //listener.onError(Thread.currentThread(), null, error, e);
                throw e;
            }
//            listener.onInfo(Thread.currentThread(), null, "file -> " + f.getAbsolutePath()
//                                                          + " delete success !");
        }

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        EnvironmentConfig ec = new EnvironmentConfig();
        ec.setAllowCreate(true);
        env = new Environment(dir, ec);
        db = env.openDatabase(null, name, dbConfig);
        lastDocID = 0;
    }

    /**
     * Returns the docid of an already seen url.
     * 
     * @param url
     *            the URL for which the docid is returned.
     * @return the docid of the url if it is seen before. Otherwise -1 is
     *         returned.
     */
    public int getDocId(String url) {
        synchronized (mutex) {
            OperationStatus result;
            DatabaseEntry value = new DatabaseEntry();
            try {
                DatabaseEntry key = new DatabaseEntry(url.getBytes());
                result = db.get(null, key, value, null);

                if (result == OperationStatus.SUCCESS && value.getData().length > 0) {
                    return StringUtil.byteArray2Int(value.getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }
    }

    public int getNewDocID(String url) {
        synchronized (mutex) {
            try {
                // Make sure that we have not already assigned a docid for this
                // URL
                int docid = getDocId(url);
                if (docid > 0) {
                    return docid;
                }

                lastDocID++;
                db.put(null, new DatabaseEntry(url.getBytes()),
                    new DatabaseEntry(StringUtil.int2ByteArray(lastDocID)));
                return lastDocID;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }
    }

    public void addUrlAndDocId(String url, int docId) throws Exception {
        synchronized (mutex) {
            if (docId <= lastDocID) {
                throw new Exception("Requested doc id: " + docId + " is not larger than: "
                                    + lastDocID);
            }

            // Make sure that we have not already assigned a docid for this URL
            int prevDocid = getDocId(url);
            if (prevDocid > 0) {
                if (prevDocid == docId) {
                    return;
                }
                throw new Exception("Doc id: " + prevDocid + " is already assigned to URL: " + url);
            }

            db.put(null, new DatabaseEntry(url.getBytes()),
                new DatabaseEntry(StringUtil.int2ByteArray(docId)));
            lastDocID = docId;
        }
    }

    public boolean isSeenBefore(String url) {
        return getDocId(url) != -1;
    }

    public int getDocCount() {
        try {
            return (int) db.count();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void sync() {
        try {
            db.sync();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            db.close();
            env.removeDatabase(null, name);
            env.cleanLog();
            env.close();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
