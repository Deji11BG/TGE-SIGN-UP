package com.example.tgesign_up;

public class TgMembersModel {



        private String memberId;
        private String memberFirstName;
        private String memberLastName;

    public TgMembersModel(String memberId, String memberFirstName, String memberLastName) {
        this.memberId = memberId;
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberFirstName() {
        return memberFirstName;
    }

    public void setMemberFirstName(String memberFirstName) {
        this.memberFirstName = memberFirstName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        this.memberLastName = memberLastName;
    }
}
