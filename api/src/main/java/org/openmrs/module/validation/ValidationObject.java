package org.openmrs.module.validation;


public class ValidationObject implements Comparable<ValidationObject>{

    String fullClassName;
    String simpleClassName;

    public ValidationObject(String fullClassName, String simpleClassName) {
        this.fullClassName = fullClassName;
        this.simpleClassName = simpleClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    public int compareTo(ValidationObject o) {
        return this.simpleClassName.compareTo(o.simpleClassName);
    }
}
