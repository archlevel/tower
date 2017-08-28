<assembly>
    <id>bin</id>
    <formats>
        <format>tar.gz</format>
    </formats>

    <!-- <includeBaseDirectory>false</includeBaseDirectory>  -->

    <dependencySets>
        <dependencySet>
            <outputDirectory>lib</outputDirectory>
            <scope>runtime</scope>
        </dependencySet>
    </dependencySets>
    
    <fileSets>
        <fileSet>
            <directory>src/main/bin</directory>
            <includes>
                <include>**</include>
            </includes>
            <fileMode>0755</fileMode>
            <outputDirectory>bin</outputDirectory>
            <filtered>true</filtered>
        </fileSet>
    </fileSets>

</assembly>
