package com.nst.yourname.model.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "icon", strict = false)
public class XMLTVIconPojo {
    @Attribute(name = "src", required = false)
    private String src;

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String str) {
        this.src = str;
    }

    public String toString() {
        return "ClassPojo [src = " + this.src + "]";
    }
}
