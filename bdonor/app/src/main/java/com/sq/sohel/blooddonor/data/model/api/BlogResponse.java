//
//
//package com.sq.sohel.blooddonor.data.model.api;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.util.List;
//
//public class BlogResponse {
//
//    @Expose
//    @SerializedName("data")
//    private List<Blog> data;
//
//    @Expose
//    @SerializedName("message")
//    private String message;
//
//    @Expose
//    @SerializedName("status_code")
//    private String statusCode;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof BlogResponse)) {
//            return false;
//        }
//
//        BlogResponse that = (BlogResponse) o;
//
//        if (!statusCode.equals(that.statusCode)) {
//            return false;
//        }
//        if (!message.equals(that.message)) {
//            return false;
//        }
//        return data.equals(that.data);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = statusCode.hashCode();
//        result = 31 * result + message.hashCode();
//        result = 31 * result + data.hashCode();
//        return result;
//    }
//
//    public List<Blog> getData() {
//        return data;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getStatusCode() {
//        return statusCode;
//    }
//
//    public static class Blog {
//
//        @Expose
//        @SerializedName("id")
//        private String Id;
//
//        @Expose
//        @SerializedName("Name")
//        private String Name;
//
//        @Expose
//        @SerializedName("Email")
//        private String Email;
//
//        @Expose
//        @SerializedName("Gender")
//        private String Gender;
//
//        @Expose
//        @SerializedName("ContactNumber")
//        private String ContactNumber;
//
//        @Expose
//        @SerializedName("longitude")
//        private Double  Longitude;
//
//        @Expose
//        @SerializedName("Latitude")
//        private Double  Latitude;
//
//        @Expose
//        @SerializedName("bloodType")
//        private String BloodType;
//
//        @Expose
//        @SerializedName("WeightLBS")
//        private Integer  WeightLBS;
//
//        @Expose
//        @SerializedName("HeightIN")
//        private Integer HeightIN;
//
//        @Expose
//        @SerializedName("LastDonationDate")
//        private String LastDonationDate;
//
//        @Expose
//        @SerializedName("address")
//        private String Address;
//
//        @Expose
//        @SerializedName("Age")
//        private Integer Age;
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) {
//                return true;
//            }
//            if (!(o instanceof Blog)) {
//                return false;
//            }
//
//            Blog blog = (Blog) o;
//            return Name.equals(blog.Name);
//
//        }
//
//        @Override
//        public int hashCode() {
//            int result = Name.hashCode();
////            result = 31 * result + coverImgUrl.hashCode();
////            result = 31 * result + title.hashCode();
////            result = 31 * result + description.hashCode();
////            result = 31 * result + author.hashCode();
////            result = 31 * result + date.hashCode();
//            return result;
//        }
//
////        public String getAuthor() {
////            return author;
////        }
////
////        public String getBlogUrl() {
////            return blogUrl;
////        }
////
////        public String getCoverImgUrl() {
////            return coverImgUrl;
////        }
////
////        public String getDate() {
////            return date;
////        }
////
////        public String getDescription() {
////            return description;
////        }
////
////        public String getTitle() {
////            return title;
////        }
//
//        public String getId() {
//            return Id;
//        }
//
//        public String getName() {
//            return Name;
//        }
//
//        public String getEmail() {
//            return Email;
//        }
//
//        public String getGender() {
//            return Gender;
//        }
//
//        public String getContactNumber() {
//            return ContactNumber;
//        }
//
//        public Double getLongitude() {
//            return Longitude;
//        }
//
//        public Double getLatitude() {
//            return Latitude;
//        }
//
//        public String getBloodType() {
//            return BloodType;
//        }
//
//        public Integer getWeightLBS() {
//            return WeightLBS;
//        }
//
//        public Integer getHeightIN() {
//            return HeightIN;
//        }
//
//        public String getLastDonationDate() {
//            return LastDonationDate;
//        }
//
//        public String getAddress() {
//            return Address;
//        }
//
//        public Integer getAge() {
//            return Age;
//        }
//    }
//}