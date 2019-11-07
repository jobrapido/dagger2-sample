package com.jobrapido.blog;


import com.jobrapido.blog.client.ClientGraph;
import com.jobrapido.blog.client.DaggerClientGraph;
import com.jobrapido.blog.dto.Gender;

public class SampleApp {

    public static void main(String... args) {
        final ClientGraph clientGraph = DaggerClientGraph.create();

        Gender stefanoGender = clientGraph.genderizeClient().genderize("Stefano");

        System.out.println("Gender: " + stefanoGender.getGenderType());
    }
}
