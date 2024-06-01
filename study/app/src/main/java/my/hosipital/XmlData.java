package my.hosipital;

public class XmlData {
    private String hName = null;
    private String hospitalSeq = null;
    private String section = null;
    private String phone = null;
    private String etc = null;
    private String add = null;

    public String getEtc(){
        return etc;
    }
    public String getPhone(){
        return phone;
    }
    public String getHName(){
        return hName;
    }
    public String getSection(){
        return section;
    }
    public String getHospitalSeq(){
        return hospitalSeq;
    }
    public String getAdd(){return add;}

    public void setAdd(String add) {
        this.add = add;
    }

    public void setHName(String hName){
        this.hName = hName;
    }
    public void setHospitalSeq(String hospitalSeq){
        this.hospitalSeq = hospitalSeq;
    }
    public void setSection(String section){
        this.section = section;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEtc(String etc){
        this.etc = etc;
    }



}
