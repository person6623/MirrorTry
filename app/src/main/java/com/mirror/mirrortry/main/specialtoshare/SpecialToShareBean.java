package com.mirror.mirrortry.main.specialtoshare;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dllo on 16/6/22.
 */
public class SpecialToShareBean implements Parcelable {

    private String result;
    private String msg;

    private DataBean data;

    protected SpecialToShareBean(Parcel in) {
        result = in.readString();
        msg = in.readString();
    }

    public static final Creator<SpecialToShareBean> CREATOR = new Creator<SpecialToShareBean>() {
        @Override
        public SpecialToShareBean createFromParcel(Parcel in) {
            return new SpecialToShareBean(in);
        }

        @Override
        public SpecialToShareBean[] newArray(int size) {
            return new SpecialToShareBean[size];
        }
    };

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeString(msg);
    }

    public static class DataBean implements Parcelable{

        private PaginationBean pagination;

        private List<ListBean> list;

        protected DataBean(Parcel in) {
            list = in.createTypedArrayList(ListBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(list);
        }

        public static class PaginationBean implements Parcelable {
            private String first_time;
            private String last_time;
            private String has_more;

            protected PaginationBean(Parcel in) {
                first_time = in.readString();
                last_time = in.readString();
                has_more = in.readString();
            }

            public static final Creator<PaginationBean> CREATOR = new Creator<PaginationBean>() {
                @Override
                public PaginationBean createFromParcel(Parcel in) {
                    return new PaginationBean(in);
                }

                @Override
                public PaginationBean[] newArray(int size) {
                    return new PaginationBean[size];
                }
            };

            public String getFirst_time() {
                return first_time;
            }

            public void setFirst_time(String first_time) {
                this.first_time = first_time;
            }

            public String getLast_time() {
                return last_time;
            }

            public void setLast_time(String last_time) {
                this.last_time = last_time;
            }

            public String getHas_more() {
                return has_more;
            }

            public void setHas_more(String has_more) {
                this.has_more = has_more;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(first_time);
                dest.writeString(last_time);
                dest.writeString(has_more);
            }
        }

        public static class ListBean implements Parcelable {
            private String story_title;
            private String story_des;
            private String story_img;
            private String story_id;
            private String story_url;
            private String if_original;
            private String original_url;
            private String from;

            private StoryDataBean story_data;

            protected ListBean(Parcel in) {
                story_title = in.readString();
                story_des = in.readString();
                story_img = in.readString();
                story_id = in.readString();
                story_url = in.readString();
                if_original = in.readString();
                original_url = in.readString();
                from = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel in) {
                    return new ListBean(in);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };

            public String getStory_title() {
                return story_title;
            }

            public void setStory_title(String story_title) {
                this.story_title = story_title;
            }

            public String getStory_des() {
                return story_des;
            }

            public void setStory_des(String story_des) {
                this.story_des = story_des;
            }

            public String getStory_img() {
                return story_img;
            }

            public void setStory_img(String story_img) {
                this.story_img = story_img;
            }

            public String getStory_id() {
                return story_id;
            }

            public void setStory_id(String story_id) {
                this.story_id = story_id;
            }

            public String getStory_url() {
                return story_url;
            }

            public void setStory_url(String story_url) {
                this.story_url = story_url;
            }

            public String getIf_original() {
                return if_original;
            }

            public void setIf_original(String if_original) {
                this.if_original = if_original;
            }

            public String getOriginal_url() {
                return original_url;
            }

            public void setOriginal_url(String original_url) {
                this.original_url = original_url;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public StoryDataBean getStory_data() {
                return story_data;
            }

            public void setStory_data(StoryDataBean story_data) {
                this.story_data = story_data;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(story_title);
                dest.writeString(story_des);
                dest.writeString(story_img);
                dest.writeString(story_id);
                dest.writeString(story_url);
                dest.writeString(if_original);
                dest.writeString(original_url);
                dest.writeString(from);
            }

            public static class StoryDataBean implements Parcelable {
                private String story_date_type;

                private List<TextArrayBean> text_array;
                private List<String> img_array;

                protected StoryDataBean(Parcel in) {
                    story_date_type = in.readString();
                    img_array = in.createStringArrayList();
                }

                public static final Creator<StoryDataBean> CREATOR = new Creator<StoryDataBean>() {
                    @Override
                    public StoryDataBean createFromParcel(Parcel in) {
                        return new StoryDataBean(in);
                    }

                    @Override
                    public StoryDataBean[] newArray(int size) {
                        return new StoryDataBean[size];
                    }
                };

                public String getStory_date_type() {
                    return story_date_type;
                }

                public void setStory_date_type(String story_date_type) {
                    this.story_date_type = story_date_type;
                }

                public List<TextArrayBean> getText_array() {
                    return text_array;
                }

                public void setText_array(List<TextArrayBean> text_array) {
                    this.text_array = text_array;
                }

                public List<String> getImg_array() {
                    return img_array;
                }

                public void setImg_array(List<String> img_array) {
                    this.img_array = img_array;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(story_date_type);
                    dest.writeStringList(img_array);
                }

                public static class TextArrayBean implements Parcelable{
                    private String verticalTitle;
                    private String verticalTitleColor;
                    private String smallTitle;
                    private String title;
                    private String titleColor;
                    private String subTitle;
                    private String colorTitle;
                    private String colorTitleColor;
                    private String category;
                    private String info_if_tag;
                    private String goodsname;
                    private String goodsprice;
                    private String goodsx;
                    private String goodsY;

                    private GoodInfoBean good_info;

                    protected TextArrayBean(Parcel in) {
                        verticalTitle = in.readString();
                        verticalTitleColor = in.readString();
                        smallTitle = in.readString();
                        title = in.readString();
                        titleColor = in.readString();
                        subTitle = in.readString();
                        colorTitle = in.readString();
                        colorTitleColor = in.readString();
                        category = in.readString();
                        info_if_tag = in.readString();
                        goodsname = in.readString();
                        goodsprice = in.readString();
                        goodsx = in.readString();
                        goodsY = in.readString();
                    }

                    public static final Creator<TextArrayBean> CREATOR = new Creator<TextArrayBean>() {
                        @Override
                        public TextArrayBean createFromParcel(Parcel in) {
                            return new TextArrayBean(in);
                        }

                        @Override
                        public TextArrayBean[] newArray(int size) {
                            return new TextArrayBean[size];
                        }
                    };

                    public String getVerticalTitle() {
                        return verticalTitle;
                    }

                    public void setVerticalTitle(String verticalTitle) {
                        this.verticalTitle = verticalTitle;
                    }

                    public String getVerticalTitleColor() {
                        return verticalTitleColor;
                    }

                    public void setVerticalTitleColor(String verticalTitleColor) {
                        this.verticalTitleColor = verticalTitleColor;
                    }

                    public String getSmallTitle() {
                        return smallTitle;
                    }

                    public void setSmallTitle(String smallTitle) {
                        this.smallTitle = smallTitle;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getTitleColor() {
                        return titleColor;
                    }

                    public void setTitleColor(String titleColor) {
                        this.titleColor = titleColor;
                    }

                    public String getSubTitle() {
                        return subTitle;
                    }

                    public void setSubTitle(String subTitle) {
                        this.subTitle = subTitle;
                    }

                    public String getColorTitle() {
                        return colorTitle;
                    }

                    public void setColorTitle(String colorTitle) {
                        this.colorTitle = colorTitle;
                    }

                    public String getColorTitleColor() {
                        return colorTitleColor;
                    }

                    public void setColorTitleColor(String colorTitleColor) {
                        this.colorTitleColor = colorTitleColor;
                    }

                    public String getCategory() {
                        return category;
                    }

                    public void setCategory(String category) {
                        this.category = category;
                    }

                    public String getInfo_if_tag() {
                        return info_if_tag;
                    }

                    public void setInfo_if_tag(String info_if_tag) {
                        this.info_if_tag = info_if_tag;
                    }

                    public String getGoodsname() {
                        return goodsname;
                    }

                    public void setGoodsname(String goodsname) {
                        this.goodsname = goodsname;
                    }

                    public String getGoodsprice() {
                        return goodsprice;
                    }

                    public void setGoodsprice(String goodsprice) {
                        this.goodsprice = goodsprice;
                    }

                    public String getGoodsx() {
                        return goodsx;
                    }

                    public void setGoodsx(String goodsx) {
                        this.goodsx = goodsx;
                    }

                    public String getGoodsY() {
                        return goodsY;
                    }

                    public void setGoodsY(String goodsY) {
                        this.goodsY = goodsY;
                    }

                    public GoodInfoBean getGood_info() {
                        return good_info;
                    }

                    public void setGood_info(GoodInfoBean good_info) {
                        this.good_info = good_info;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(verticalTitle);
                        dest.writeString(verticalTitleColor);
                        dest.writeString(smallTitle);
                        dest.writeString(title);
                        dest.writeString(titleColor);
                        dest.writeString(subTitle);
                        dest.writeString(colorTitle);
                        dest.writeString(colorTitleColor);
                        dest.writeString(category);
                        dest.writeString(info_if_tag);
                        dest.writeString(goodsname);
                        dest.writeString(goodsprice);
                        dest.writeString(goodsx);
                        dest.writeString(goodsY);
                    }

                    public static class GoodInfoBean implements Parcelable{
                        private String goods_id;
                        private String goods_pic;
                        private String model;
                        private String goods_img;
                        private String goods_name;
                        private String last_storge;
                        private String whole_storge;
                        private String height;
                        private String ordain;
                        private String product_area;
                        private String goods_price;
                        private String discount_price;
                        private String brand;
                        private String info_des;
                        private String goods_share;

                        private List<GoodsDataBean> goods_data;

                        private List<DesignDesBean> design_des;

                        protected GoodInfoBean(Parcel in) {
                            goods_id = in.readString();
                            goods_pic = in.readString();
                            model = in.readString();
                            goods_img = in.readString();
                            goods_name = in.readString();
                            last_storge = in.readString();
                            whole_storge = in.readString();
                            height = in.readString();
                            ordain = in.readString();
                            product_area = in.readString();
                            goods_price = in.readString();
                            discount_price = in.readString();
                            brand = in.readString();
                            info_des = in.readString();
                            goods_share = in.readString();
                        }

                        public static final Creator<GoodInfoBean> CREATOR = new Creator<GoodInfoBean>() {
                            @Override
                            public GoodInfoBean createFromParcel(Parcel in) {
                                return new GoodInfoBean(in);
                            }

                            @Override
                            public GoodInfoBean[] newArray(int size) {
                                return new GoodInfoBean[size];
                            }
                        };

                        public String getGoods_id() {
                            return goods_id;
                        }

                        public void setGoods_id(String goods_id) {
                            this.goods_id = goods_id;
                        }

                        public String getGoods_pic() {
                            return goods_pic;
                        }

                        public void setGoods_pic(String goods_pic) {
                            this.goods_pic = goods_pic;
                        }

                        public String getModel() {
                            return model;
                        }

                        public void setModel(String model) {
                            this.model = model;
                        }

                        public String getGoods_img() {
                            return goods_img;
                        }

                        public void setGoods_img(String goods_img) {
                            this.goods_img = goods_img;
                        }

                        public String getGoods_name() {
                            return goods_name;
                        }

                        public void setGoods_name(String goods_name) {
                            this.goods_name = goods_name;
                        }

                        public String getLast_storge() {
                            return last_storge;
                        }

                        public void setLast_storge(String last_storge) {
                            this.last_storge = last_storge;
                        }

                        public String getWhole_storge() {
                            return whole_storge;
                        }

                        public void setWhole_storge(String whole_storge) {
                            this.whole_storge = whole_storge;
                        }

                        public String getHeight() {
                            return height;
                        }

                        public void setHeight(String height) {
                            this.height = height;
                        }

                        public String getOrdain() {
                            return ordain;
                        }

                        public void setOrdain(String ordain) {
                            this.ordain = ordain;
                        }

                        public String getProduct_area() {
                            return product_area;
                        }

                        public void setProduct_area(String product_area) {
                            this.product_area = product_area;
                        }

                        public String getGoods_price() {
                            return goods_price;
                        }

                        public void setGoods_price(String goods_price) {
                            this.goods_price = goods_price;
                        }

                        public String getDiscount_price() {
                            return discount_price;
                        }

                        public void setDiscount_price(String discount_price) {
                            this.discount_price = discount_price;
                        }

                        public String getBrand() {
                            return brand;
                        }

                        public void setBrand(String brand) {
                            this.brand = brand;
                        }

                        public String getInfo_des() {
                            return info_des;
                        }

                        public void setInfo_des(String info_des) {
                            this.info_des = info_des;
                        }

                        public String getGoods_share() {
                            return goods_share;
                        }

                        public void setGoods_share(String goods_share) {
                            this.goods_share = goods_share;
                        }

                        public List<GoodsDataBean> getGoods_data() {
                            return goods_data;
                        }

                        public void setGoods_data(List<GoodsDataBean> goods_data) {
                            this.goods_data = goods_data;
                        }

                        public List<DesignDesBean> getDesign_des() {
                            return design_des;
                        }

                        public void setDesign_des(List<DesignDesBean> design_des) {
                            this.design_des = design_des;
                        }

                        @Override
                        public int describeContents() {
                            return 0;
                        }

                        @Override
                        public void writeToParcel(Parcel dest, int flags) {
                            dest.writeString(goods_id);
                            dest.writeString(goods_pic);
                            dest.writeString(model);
                            dest.writeString(goods_img);
                            dest.writeString(goods_name);
                            dest.writeString(last_storge);
                            dest.writeString(whole_storge);
                            dest.writeString(height);
                            dest.writeString(ordain);
                            dest.writeString(product_area);
                            dest.writeString(goods_price);
                            dest.writeString(discount_price);
                            dest.writeString(brand);
                            dest.writeString(info_des);
                            dest.writeString(goods_share);
                        }

                        public static class GoodsDataBean implements Parcelable{
                            private String introContent;
                            private String cellHeight;
                            private String name;
                            private String location;
                            private String country;
                            private String english;

                            protected GoodsDataBean(Parcel in) {
                                introContent = in.readString();
                                cellHeight = in.readString();
                                name = in.readString();
                                location = in.readString();
                                country = in.readString();
                                english = in.readString();
                            }

                            public static final Creator<GoodsDataBean> CREATOR = new Creator<GoodsDataBean>() {
                                @Override
                                public GoodsDataBean createFromParcel(Parcel in) {
                                    return new GoodsDataBean(in);
                                }

                                @Override
                                public GoodsDataBean[] newArray(int size) {
                                    return new GoodsDataBean[size];
                                }
                            };

                            public String getIntroContent() {
                                return introContent;
                            }

                            public void setIntroContent(String introContent) {
                                this.introContent = introContent;
                            }

                            public String getCellHeight() {
                                return cellHeight;
                            }

                            public void setCellHeight(String cellHeight) {
                                this.cellHeight = cellHeight;
                            }

                            public String getName() {
                                return name;
                            }

                            public void setName(String name) {
                                this.name = name;
                            }

                            public String getLocation() {
                                return location;
                            }

                            public void setLocation(String location) {
                                this.location = location;
                            }

                            public String getCountry() {
                                return country;
                            }

                            public void setCountry(String country) {
                                this.country = country;
                            }

                            public String getEnglish() {
                                return english;
                            }

                            public void setEnglish(String english) {
                                this.english = english;
                            }

                            @Override
                            public int describeContents() {
                                return 0;
                            }

                            @Override
                            public void writeToParcel(Parcel dest, int flags) {
                                dest.writeString(introContent);
                                dest.writeString(cellHeight);
                                dest.writeString(name);
                                dest.writeString(location);
                                dest.writeString(country);
                                dest.writeString(english);
                            }
                        }

                        public static class DesignDesBean implements Parcelable{
                            private String img;
                            private String cellHeight;
                            private String type;

                            protected DesignDesBean(Parcel in) {
                                img = in.readString();
                                cellHeight = in.readString();
                                type = in.readString();
                            }

                            public static final Creator<DesignDesBean> CREATOR = new Creator<DesignDesBean>() {
                                @Override
                                public DesignDesBean createFromParcel(Parcel in) {
                                    return new DesignDesBean(in);
                                }

                                @Override
                                public DesignDesBean[] newArray(int size) {
                                    return new DesignDesBean[size];
                                }
                            };

                            public String getImg() {
                                return img;
                            }

                            public void setImg(String img) {
                                this.img = img;
                            }

                            public String getCellHeight() {
                                return cellHeight;
                            }

                            public void setCellHeight(String cellHeight) {
                                this.cellHeight = cellHeight;
                            }

                            public String getType() {
                                return type;
                            }

                            public void setType(String type) {
                                this.type = type;
                            }

                            @Override
                            public int describeContents() {
                                return 0;
                            }

                            @Override
                            public void writeToParcel(Parcel dest, int flags) {
                                dest.writeString(img);
                                dest.writeString(cellHeight);
                                dest.writeString(type);
                            }
                        }
                    }
                }
            }
        }
    }
}
