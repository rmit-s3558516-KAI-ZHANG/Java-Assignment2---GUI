package application;

import java.io.IOException;

import OwnException.ConflictRelationshipException;
import OwnException.NotToBeClassmatesException;
import OwnException.NotToBeParentException;
import OwnInterface.Connection;

public abstract class Person implements Connection{
    private String name;
    private String gender;
    private int age;
    private String status;
    private String profile_image;
    private String state;
    public Person(String name, String image,String status,String gender,int age,String state) {
        this.name = name;
        this.profile_image=image;
        this.gender = gender;
        this.age = age;
        this.status = status;
        this.state=state;
    }
    /**
     * 
     * @param p1
     * @param p2
     * @param re
     * @throws ConflictRelationshipException 
     * @throws NotToBeClassmatesException 
     * @throws IOException
     */
    
    @Override
    public int hashCode() {
        return 1;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Person)) return false;
        else if (((Person)obj).getName().equals(getName())) return true;
        return false;
        
    }
    
    @Override
    public void ToBeClassmate(Connection co) throws  ConflictRelationshipException, NotToBeClassmatesException {
        if(((Person)co).getName().equals(this.getName())){
            throw new ConflictRelationshipException("can not add itself");
        }
        if(!(co instanceof YoungChild)){
            Mininet.relat.add(new Relationships(this, (Person) co, "classmates"));
        }else{
            throw new NotToBeClassmatesException("making a young child in a classmate relation!", ((Person) co).getAge());
        }
    }
    
    @Override
    public void ToBeParent(Connection co) throws NotToBeParentException, ConflictRelationshipException {
        if(((Person)co).getName().equals(this.getName())){
            throw new ConflictRelationshipException("can not add itself");
        }
        if (co instanceof Adult) {
            Mininet.relat.add(new Relationships(this, (Person) co, "parent"));
        } else {
            throw new NotToBeParentException("adding a child as a parent");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
