package com.tiaoin.crawl.core.component.collection;

import java.util.ArrayList;
import java.util.List;

import com.tiaoin.crawl.core.component.Component;
import com.tiaoin.crawl.core.component.loader.AbstractComponentLoader;

public class SpiderComponentCollectionServiceImpl extends AbstractComponentLoader implements ComponentCollectionService {
    private List<Component> components;

    @Override
    public void init() {
        this.loader();
    }

    @Override
    public void register(Component component) {
        if(null == components)  {
            components = new ArrayList<Component>();
        }
        
        if(!components.contains(component)) {
            components.add(component);
        }
    }

    @Override
    public List<Component> getComponent() {
        return components;
    }

}
