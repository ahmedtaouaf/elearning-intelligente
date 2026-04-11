package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Tables {
    @GetMapping("/tables/custom")
    public String custom() {
        return "tables/custom";
    }

    @GetMapping("/tables/static")
    public String tableStatic() {
        return "tables/static";
    }

    @GetMapping("/tables/datatables/add-rows")
    public String addRows() {
        return "tables/datatables/add-rows";
    }

    @GetMapping("/tables/datatables/ajax")
    public String ajax() {
        return "tables/datatables/ajax";
    }

    @GetMapping("/tables/datatables/basic")
    public String basic() {
        return "tables/datatables/basic";
    }

    @GetMapping("/tables/datatables/checkbox-select")
    public String checkboxSelect() {
        return "tables/datatables/checkbox-select";
    }

    @GetMapping("/tables/datatables/child-rows")
    public String childRows() {
        return "tables/datatables/child-rows";
    }

    @GetMapping("/tables/datatables/column-searching")
    public String columnSearching() {
        return "tables/datatables/column-searching";
    }

    @GetMapping("/tables/datatables/columns")
    public String columns() {
        return "tables/datatables/columns";
    }

    @GetMapping("/tables/datatables/export-data")
    public String exportData() {
        return "tables/datatables/export-data";
    }

    @GetMapping("/tables/datatables/fixed-columns")
    public String fixedColumns() {
        return "tables/datatables/fixed-columns";
    }

    @GetMapping("/tables/datatables/fixed-header")
    public String fixedHeader() {
        return "tables/datatables/fixed-header";
    }

    @GetMapping("/tables/datatables/javascript")
    public String javascript() {
        return "tables/datatables/javascript";
    }

    @GetMapping("/tables/datatables/range-search")
    public String rangeSearch() {
        return "tables/datatables/range-search";
    }

    @GetMapping("/tables/datatables/rendering")
    public String rendering() {
        return "tables/datatables/rendering";
    }
    
    @GetMapping("/tables/datatables/scroll")
    public String scroll() {
        return "tables/datatables/scroll";
    }

    @GetMapping("/tables/datatables/select")
    public String select() {
        return "tables/datatables/select";
    }
}