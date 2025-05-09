package com.durian.durian_api.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;          // Danh sách dữ liệu
    private int page;                 // Trang hiện tại
    private int size;                 // Kích thước trang
    private long totalElements;       // Tổng số phần tử
    private int totalPages;           // Tổng số trang
    private boolean last;             // Trang cuối cùng?
    private boolean first;
}
