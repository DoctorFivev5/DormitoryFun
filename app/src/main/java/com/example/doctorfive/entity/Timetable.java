package com.example.doctorfive.entity;

/**
 * Created by DoctorFive on 2017/12/12.
 * 课表实体类
 */

public class Timetable {

    private int id;
    private String stuNum;
    private int term;
    private String class1;
    private String class2;
    private String class3;
    private String class4;
    private String class5;
    private String class6;
    private String class7;
    private String class8;
    private String class9;
    private String class10;
    private String class11;
    private String class12;
    private String class13;
    private String class14;
    private String class15;
    private String class16;
    private String class17;
    private String class18;
    private String class19;
    private String class20;
    private String class21;
    private String class22;
    private String class23;
    private String class24;
    private String class25;
    private String class26;
    private String class27;
    private String class28;
    private String class29;
    private String class30;
    private String class31;
    private String class32;
    private String class33;
    private String class34;
    private String class35;
    private String class36;
    private String class37;
    private String class38;
    private String class39;
    private String class40;
    private String class41;
    private String class42;
    private String class43;
    private String class44;
    private String class45;
    private String class46;
    private String class47;
    private String class48;
    private String class49;

    public Timetable(){

    }

    public Timetable(String[] classes, String stuNum, int term){
        this.stuNum = stuNum;
        class1 = classes[0];
        class2 = classes[1];
        class3 = classes[2];
        class4 = classes[3];
        class5 = classes[4];
        class6 = classes[5];
        class7 = classes[6];
        class8 = classes[7];
        class9 = classes[8];
        class10 = classes[9];
        class11 = classes[10];
        class12 = classes[11];
        class13 = classes[12];
        class14 = classes[13];
        class15 = classes[14];
        class16 = classes[15];
        class17 = classes[16];
        class18 = classes[17];
        class19 = classes[18];
        class20 = classes[19];
        class21 = classes[20];
        class22 = classes[21];
        class23 = classes[22];
        class24 = classes[23];
        class25 = classes[24];
        class26 = classes[25];
        class27 = classes[26];
        class28 = classes[27];
        class29 = classes[28];
        class30 = classes[29];
        class31 = classes[30];
        class32 = classes[31];
        class33 = classes[32];
        class34 = classes[33];
        class35 = classes[34];
        class36 = classes[35];
        class37 = classes[36];
        class38 = classes[37];
        class39 = classes[38];
        class40 = classes[39];
        class41 = classes[40];
        class42 = classes[41];
        class43 = classes[42];
        class44 = classes[43];
        class45 = classes[44];
        class46 = classes[45];
        class47 = classes[46];
        class48 = classes[47];
        class49 = classes[48];
        this.term = term;
    }

    public String[] getClasses(){
        String[] classes = new String[49];
        classes[0] = class1;
        classes[1] = class2;
        classes[2] = class3;
        classes[3] = class4;
        classes[4] = class5;
        classes[5] = class6;
        classes[6] = class7;
        classes[7] = class8;
        classes[8] = class9;
        classes[9] = class10 ;
        classes[10] = class11;
        classes[11] = class12;
        classes[12] = class13;
        classes[13] = class14;
        classes[14] = class15;
        classes[15] = class16;
        classes[16] = class17;
        classes[17] = class18;
        classes[18] = class19;
        classes[19] = class20;
        classes[20] = class21;
        classes[21] = class22;
        classes[22] = class23;
        classes[23] = class24;
        classes[24] = class25;
        classes[25] = class26;
        classes[26] = class27;
        classes[27] = class28;
        classes[28] = class29;
        classes[29] = class30;
        classes[30] = class31;
        classes[31] = class32;
        classes[32] = class33;
        classes[33] = class34;
        classes[34] = class35;
        classes[35] = class36;
        classes[36] = class37;
        classes[37] = class38;
        classes[38] = class39;
        classes[39] = class40;
        classes[40] = class41;
        classes[41] = class42;
        classes[42] = class43;
        classes[43] = class44;
        classes[44] = class45;
        classes[45] = class46;
        classes[46] = class47;
        classes[47] = class48;
        classes[48] = class49;
        return classes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuNum(){
        return stuNum;
    }
    public void setStuNum(String stuNum){
        this.stuNum = stuNum;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }


    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getClass2() {
        return class2;
    }

    public void setClass2(String class2) {
        this.class2 = class2;
    }

    public String getClass3() {
        return class3;
    }

    public void setClass3(String class3) {
        this.class3 = class3;
    }

    public String getClass4() {
        return class4;
    }

    public void setClass4(String class4) {
        this.class4 = class4;
    }

    public String getClass5() {
        return class5;
    }

    public void setClass5(String class5) {
        this.class5 = class5;
    }

    public String getClass6() {
        return class6;
    }

    public void setClass6(String class6) {
        this.class6 = class6;
    }

    public String getClass7() {
        return class7;
    }

    public void setClass7(String class7) {
        this.class7 = class7;
    }

    public String getClass8() {
        return class8;
    }

    public void setClass8(String class8) {
        this.class8 = class8;
    }

    public String getClass9() {
        return class9;
    }

    public void setClass9(String class9) {
        this.class9 = class9;
    }

    public String getClass10() {
        return class10;
    }

    public void setClass10(String class10) {
        this.class10 = class10;
    }

    public String getClass11() {
        return class11;
    }

    public void setClass11(String class11) {
        this.class11 = class11;
    }

    public String getClass12() {
        return class12;
    }

    public void setClass12(String class12) {
        this.class12 = class12;
    }

    public String getClass13() {
        return class13;
    }

    public void setClass13(String class13) {
        this.class13 = class13;
    }

    public String getClass14() {
        return class14;
    }

    public void setClass14(String class14) {
        this.class14 = class14;
    }

    public String getClass15() {
        return class15;
    }

    public void setClass15(String class15) {
        this.class15 = class15;
    }

    public String getClass16() {
        return class16;
    }

    public void setClass16(String class16) {
        this.class16 = class16;
    }

    public String getClass17() {
        return class17;
    }

    public void setClass17(String class17) {
        this.class17 = class17;
    }

    public String getClass18() {
        return class18;
    }

    public void setClass18(String class18) {
        this.class18 = class18;
    }

    public String getClass19() {
        return class19;
    }

    public void setClass19(String class19) {
        this.class19 = class19;
    }

    public String getClass20() {
        return class20;
    }

    public void setClass20(String class20) {
        this.class20 = class20;
    }

    public String getClass21() {
        return class21;
    }

    public void setClass21(String class21) {
        this.class21 = class21;
    }

    public String getClass22() {
        return class22;
    }

    public void setClass22(String class22) {
        this.class22 = class22;
    }

    public String getClass23() {
        return class23;
    }

    public void setClass23(String class23) {
        this.class23 = class23;
    }

    public String getClass24() {
        return class24;
    }

    public void setClass24(String class24) {
        this.class24 = class24;
    }

    public String getClass25() {
        return class25;
    }

    public void setClass25(String class25) {
        this.class25 = class25;
    }

    public String getClass26() {
        return class26;
    }

    public void setClass26(String class26) {
        this.class26 = class26;
    }

    public String getClass27() {
        return class27;
    }

    public void setClass27(String class27) {
        this.class27 = class27;
    }

    public String getClass28() {
        return class28;
    }

    public void setClass28(String class28) {
        this.class28 = class28;
    }

    public String getClass29() {
        return class29;
    }

    public void setClass29(String class29) {
        this.class29 = class29;
    }

    public String getClass30() {
        return class30;
    }

    public void setClass30(String class30) {
        this.class30 = class30;
    }

    public String getClass31() {
        return class31;
    }

    public void setClass31(String class31) {
        this.class31 = class31;
    }

    public String getClass32() {
        return class32;
    }

    public void setClass32(String class32) {
        this.class32 = class32;
    }

    public String getClass33() {
        return class33;
    }

    public void setClass33(String class33) {
        this.class33 = class33;
    }

    public String getClass34() {
        return class34;
    }

    public void setClass34(String class34) {
        this.class34 = class34;
    }

    public String getClass35() {
        return class35;
    }

    public void setClass35(String class35) {
        this.class35 = class35;
    }

    public String getClass36() {
        return class36;
    }

    public void setClass36(String class36) {
        this.class36 = class36;
    }

    public String getClass37() {
        return class37;
    }

    public void setClass37(String class37) {
        this.class37 = class37;
    }

    public String getClass38() {
        return class38;
    }

    public void setClass38(String class38) {
        this.class38 = class38;
    }

    public String getClass39() {
        return class39;
    }

    public void setClass39(String class39) {
        this.class39 = class39;
    }

    public String getClass40() {
        return class40;
    }

    public void setClass40(String class40) {
        this.class40 = class40;
    }

    public String getClass41() {
        return class41;
    }

    public void setClass41(String class41) {
        this.class41 = class41;
    }

    public String getClass42() {
        return class42;
    }

    public void setClass42(String class42) {
        this.class42 = class42;
    }

    public String getClass43() {
        return class43;
    }

    public void setClass43(String class43) {
        this.class43 = class43;
    }

    public String getClass44() {
        return class44;
    }

    public void setClass44(String class44) {
        this.class44 = class44;
    }

    public String getClass45() {
        return class45;
    }

    public void setClass45(String class45) {
        this.class45 = class45;
    }

    public String getClass46() {
        return class46;
    }

    public void setClass46(String class46) {
        this.class46 = class46;
    }

    public String getClass47() {
        return class47;
    }

    public void setClass47(String class47) {
        this.class47 = class47;
    }

    public String getClass48() {
        return class48;
    }

    public void setClass48(String class48) {
        this.class48 = class48;
    }

    public String getClass49() {
        return class49;
    }

    public void setClass49(String class49) {
        this.class49 = class49;
    }
}


