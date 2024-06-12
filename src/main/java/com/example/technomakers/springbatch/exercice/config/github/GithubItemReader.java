package com.example.technomakers.springbatch.exercice.config.github;

import com.example.technomakers.springbatch.exercice.model.Github;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class GithubItemReader extends FlatFileItemReader<Github> {

    public GithubItemReader() {
        setResource(new ClassPathResource("prs.csv"));
        setLinesToSkip(1);
        setLineMapper(new DefaultLineMapper<Github>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("name", "year", "quarter","count");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Github>() {{
                setTargetType(Github.class);
            }});
        }});
    }
}
