package com.example.technomakers.springbatch.exercice.config.github;

import com.example.technomakers.springbatch.exercice.model.Github;
import org.springframework.batch.item.ItemProcessor;

public class GithubItemProcessor implements ItemProcessor<Github, Github> {

    @Override
    public Github process(Github github) throws Exception {
        github.setName(github.getName().toLowerCase());
        return github;
    }
}
