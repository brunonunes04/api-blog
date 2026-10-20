package com.api.api_blog.repository;

import com.api.api_blog.model.PostModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
public class PostRepositoryTest {

    @Autowired
    private PostRepository repository;

    @Test
    void deveSalvarERetornarPost(){

        //Arrange
        PostModel post = new PostModel("Henrique", "Alunos +devs", "Os alunos são do +devs");

        //Act
        PostModel postSalvo = repository.save(post);

        //Assert
        Assertions.assertThat(postSalvo).isNotNull();
    }

    @Test
    void deveListarPorTitulo(){

        //Arrange
        PostModel post1 = new PostModel("Bruno", "Aluno do +devs", "Esse aluno é bom");
        PostModel post2 = new PostModel("Bernardo", "Aluno do +devs", "Esse aluno é ótimo");
        PostModel post3 = new PostModel("Giovani", "Aluno do +devs", "Esse aluno é muito bom");

        repository.save(post1);
        repository.save(post2);
        repository.save(post3);

        //Act
        Page<PostModel> resultado = repository.findByTituloContainingIgnoreCase("Aluno do +devs", PageRequest.of(0, 10));

        //Assert
        Assertions.assertThat(resultado).hasSize(3);
    }
}
