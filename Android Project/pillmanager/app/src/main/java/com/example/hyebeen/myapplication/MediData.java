package com.example.hyebeen.myapplication;

public class MediData{

    //------------Field--------------//
    private int num;

    private String name;
    private String info;
    private String caution;
    private String donot;
    private String img;

    private int all;
    private int one;

    private int cnt;

    //---------Getter & Setter------------//

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }

    public String getMember_info() {
        return info;
    }
    public void setMember_info(String member_info) {
        this.info = member_info;
    }

    public String getMember_name() {
        return name;
    }
    public void setMember_name(String member_name) {
        this.name = member_name;
    }

    public String getMember_caution() {
        return caution;
    }
    public void setMember_caution(String member_take) {
        this.caution = member_take;
    }

    public String getMember_donot() {
        return donot;
    }
    public void setMember_donot(String donot) {
        this.donot = donot;
    }

    public String getMember_img() {
        return img;
    }
    public void setMember_img(String img) {
        this.img = img;
    }

}
