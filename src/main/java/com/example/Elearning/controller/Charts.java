package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Charts {
    @GetMapping("/charts/apex/area")
    public String apexArea() {
        return "charts/apex/area";
    }

    @GetMapping("/charts/apex/bar")
    public String apexBar() {
        return "charts/apex/bar";
    }

    @GetMapping("/charts/apex/boxplot")
    public String apexBoxplot() {
        return "charts/apex/boxplot";
    }

    @GetMapping("/charts/apex/bubble")
    public String apexBubble() {
        return "charts/apex/bubble";
    }

    @GetMapping("/charts/apex/candlestick")
    public String apexCandlestick() {
        return "charts/apex/candlestick";
    }

    @GetMapping("/charts/apex/funnel")
    public String apexFunnel() {
        return "charts/apex/funnel";
    }

    @GetMapping("/charts/apex/column")
    public String apexColumn() {
        return "charts/apex/column";
    }

    @GetMapping("/charts/apex/heatmap")
    public String apexHeatmap() {
        return "charts/apex/heatmap";
    }

    @GetMapping("/charts/apex/line")
    public String apexLine() {
        return "charts/apex/line";
    }

    @GetMapping("/charts/apex/mixed")
    public String apexMixed() {
        return "charts/apex/mixed";
    }

    @GetMapping("/charts/apex/timeline")
    public String apexTimeline() {
        return "charts/apex/timeline";
    }

    @GetMapping("/charts/apex/slope")
    public String apexslope() {
        return "charts/apex/slope";
    }

    @GetMapping("/charts/apex/treemap")
    public String apexTreemap() {
        return "charts/apex/treemap";
    }

    @GetMapping("/charts/apex/pie")
    public String apexPie() {
        return "charts/apex/pie";
    }

    @GetMapping("/charts/apex/radar")
    public String apexRadar() {
        return "charts/apex/radar";
    }

    @GetMapping("/charts/apex/radialbar")
    public String apexRadialBar() {
        return "charts/apex/radialbar";
    }

    @GetMapping("/charts/apex/range")
    public String apexRange() {
        return "charts/apex/range";
    }

    @GetMapping("/charts/apex/scatter")
    public String apexScatter() {
        return "charts/apex/scatter";
    }

    @GetMapping("/charts/apex/polar-area")
    public String apexPolarArea() {
        return "charts/apex/polar-area";
    }

    @GetMapping("/charts/apex/sparklines")
    public String apexSparklines() {
        return "charts/apex/sparklines";
    }

    @GetMapping("/charts/apextree")
    public String apexTree() {
        return "charts/apextree";
    }

    @GetMapping("/charts/apexsankey")
    public String apexSankey() {
        return "charts/apexsankey";
    }

    @GetMapping("/charts/chartjs/area")
    public String chartjsArea() {
        return "charts/chartjs/area";
    }

    @GetMapping("/charts/chartjs/bar")
    public String chartjsBar() {
        return "charts/chartjs/bar";
    }

    @GetMapping("/charts/chartjs/line")
    public String chartjsLine() {
        return "charts/chartjs/line";
    }

    @GetMapping("/charts/chartjs/other")
    public String chartjsOther() {
        return "charts/chartjs/other";
    }
}