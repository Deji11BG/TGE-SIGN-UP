package com.example.tgesign_up;

public class TgMembersModel {



        private String memberId;
        private String uniqueMemberId;
        private String memberName;
        private String memberVillage;

    public TgMembersModel(String memberId, String uniqueMemberId, String memberName, String memberVillage) {
        this.memberId = memberId;
        this.uniqueMemberId = uniqueMemberId;
        this.memberName = memberName;
        this.memberVillage = memberVillage;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUniqueMemberId() {
        return uniqueMemberId;
    }

    public void setUniqueMemberId(String uniqueMemberId) {
        this.uniqueMemberId = uniqueMemberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberVillage() {
        return memberVillage;
    }

    public void setMemberVillage(String memberVillage) {
        this.memberVillage = memberVillage;
    }
}
