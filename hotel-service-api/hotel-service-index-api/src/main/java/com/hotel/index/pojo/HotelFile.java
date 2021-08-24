package com.hotel.index.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_hotel_file")
public class HotelFile {

    /**
     * 映射编号
     * */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 图片id
     * */
    @Column(name = "file_id")
    private Integer fileId;

    /**
     * 房源编号
     * */
    @Column(name = "hotel_id")
    private Integer hotelId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }
}
