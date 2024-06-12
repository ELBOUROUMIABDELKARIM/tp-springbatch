package com.example.technomakers.springbatch.exercice.config.github;

import com.example.technomakers.springbatch.exercice.model.Github;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GithubItemWriter extends FlatFileItemWriter<Github> {
    private static final String HEADER = "Name,Year,Quarter,Count";
    private static final String FILE_NAME = "edited_prs.csv";

    public GithubItemWriter() {
        setResource(new FileSystemResource(FILE_NAME));
        setLineAggregator(new DelimitedLineAggregator<Github>() {{
            setDelimiter(",");
            setFieldExtractor(github -> new Object[] {
                    github.getName(), github.getYear(), github.getQuarter(), github.getCount()
            });
        }});
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        super.open(executionContext);
        writeHeader();
    }

    private void writeHeader() {
        Path path = Paths.get(FILE_NAME);
        if (!Files.exists(path) || isFileEmpty(path)) {
            try (Writer writer = Files.newBufferedWriter(path)) {
                writer.write(HEADER);
                writer.write(System.lineSeparator());
            } catch (IOException e) {
                throw new ItemStreamException("Erreur de création de header", e);
            }
        }
    }

    private boolean isFileEmpty(Path path) {
        try {
            return Files.size(path) == 0;
        } catch (IOException e) {
            throw new ItemStreamException("Pas de possibilté de vérifier l'xistance de fichier", e);
        }
    }
}
