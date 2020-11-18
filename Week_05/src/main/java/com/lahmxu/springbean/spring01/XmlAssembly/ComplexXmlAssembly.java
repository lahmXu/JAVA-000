package com.lahmxu.springbean.spring01.XmlAssembly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ComplexXmlAssembly implements Serializable {
    private final static long serialVersionUID = -4010064039804106408L;

    private Long id;
    private List<String> list;
    private Map<String, String> map;
    private Properties properties;
    private Set<String> set;
    private String[] array;

}
