package com.tower.service.json.impl;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ro.nextreports.engine.exporter.ResultExporter;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class JsonDeserializeBenchmark {
    /**
     * 反序列化次数参数
     */
    @Param({"1000", "10000", "100000"})
    private int count;

    private String jsonStr;

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(JsonDeserializeBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(0)
                .build();
        Collection<RunResult> results =  new Runner(opt).run();
        ResultExporter.exportResult("JSON反序列化性能", results, "count", "秒");
    }

    @Benchmark
    public void Gson() {
        for (int i = 0; i < count; i++) {
            GsonImpl.getInstance().json2Bean(jsonStr, Person.class);
        }
    }

    @Benchmark
    public void FastJson() {
        for (int i = 0; i < count; i++) {
            FastJsonImpl.getInstance().json2Bean(jsonStr, Person.class);
        }
    }

    @Benchmark
    public void Jackson() {
        for (int i = 0; i < count; i++) {
            JacksonImpl.getInstance().json2Bean(jsonStr, Person.class);
        }
    }

    @Setup
    public void prepare() {
        jsonStr="{\"name\":\"邵同学\",\"fullName\":{\"firstName\":\"zjj_first\",\"middleName\":\"zjj_middle\",\"lastName\":\"zjj_last\"},\"age\":24,\"birthday\":null,\"hobbies\":[\"篮球\",\"游泳\",\"coding\"],\"clothes\":{\"shoes\":\"安踏\",\"trousers\":\"adidas\",\"coat\":\"Nike\"},\"friends\":[{\"name\":\"小明\",\"fullName\":{\"firstName\":\"xxx_first\",\"middleName\":\"xxx_middle\",\"lastName\":\"xxx_last\"},\"age\":24,\"birthday\":null,\"hobbies\":[\"篮球\",\"游泳\",\"coding\"],\"clothes\":{\"shoes\":\"安踏\",\"trousers\":\"adidas\",\"coat\":\"Nike\"},\"friends\":null},{\"name\":\"Tony\",\"fullName\":{\"firstName\":\"xxx_first\",\"middleName\":\"xxx_middle\",\"lastName\":\"xxx_last\"},\"age\":24,\"birthday\":null,\"hobbies\":[\"篮球\",\"游泳\",\"coding\"],\"clothes\":{\"shoes\":\"安踏\",\"trousers\":\"adidas\",\"coat\":\"Nike\"},\"friends\":null},{\"name\":\"陈小二\",\"fullName\":{\"firstName\":\"xxx_first\",\"middleName\":\"xxx_middle\",\"lastName\":\"xxx_last\"},\"age\":24,\"birthday\":null,\"hobbies\":[\"篮球\",\"游泳\",\"coding\"],\"clothes\":{\"shoes\":\"安踏\",\"trousers\":\"adidas\",\"coat\":\"Nike\"},\"friends\":null}]}";
    }

    @TearDown
    public void shutdown() {
    }

}
