package com.example.spoon;


import java.util.ArrayList;



public class Profile {
    private int _id;
    private int _age;
    private String _name;
    private Data _profile_data;
    private String _gender;

    public Profile( int _age, String _name, String _gender) {
        this._age = _age;
        this._name = _name;
        this._gender = _gender;
    }

    public Profile() {
    }

    public int get_id() {
        return _id;
    }

    public int get_age() {
        return _age;
    }

    public String get_name() {
        return _name;
    }

    public Data get_profile_data() {
        return _profile_data;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_profile_data(Data _profile_data) {
        this._profile_data = _profile_data;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public static Profile getProfile (ArrayList<Profile> p , String n)
    {
        Profile rp = new Profile(0,"","");
        for(Profile i : p)
        {
            if (i.get_name().equals(n)) {
                rp.set_name(i.get_name());
                rp.set_gender(i.get_gender());
                rp.set_age(i.get_age());
                rp.set_id(i.get_id());
            }

        }
        return rp;
    }
}

