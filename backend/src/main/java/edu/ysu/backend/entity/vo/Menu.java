package edu.ysu.backend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private Integer mid;
    private String name;
    private String path;
    private Integer parent;
    private String memo;
    private Integer del;
    private String icon;
    private String value;
    private String label;
    private List<Menu> children;
}
