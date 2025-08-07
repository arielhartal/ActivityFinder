package com.example.user.activityfinder;

public class User {
    private String email;
    private String userid;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String category;
    private String event;
    private String address;

    public User() {}



    public User(String email, String password, String firstName, String lastName, int age, String
        gender,String userid, /*String category,*/String event,String address){
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
            this.userid = email.replace('.', ',');
            this.category = "";
            this.event=event;
            this.address=address;
        }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

   public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEmail () {
            return email;
        }

        public void setEmail (String email){
            this.email = email;
        }

        public String getUserid () {
            return userid;
        }

        public void setUserid (String userid){
            this.userid = userid;
        }

        public String getPassword () {
            return password;
        }

        public void setPassword (String password){
            this.password = password;
        }

        public String getFirstName () {
            return firstName;
        }

        public void setFirstName (String firstName){
            this.firstName = firstName;
        }

        public String getLastName () {
            return lastName;
        }

        public void setLastName (String lastName){
            this.lastName = lastName;
        }

        public int getAge () {
            return age;
        }

        public void setAge ( int age){
            this.age = age;
        }

        public String getGender () {
            return gender;
        }

        public void setGender (String gender){
            this.gender = gender;
        }


    }
