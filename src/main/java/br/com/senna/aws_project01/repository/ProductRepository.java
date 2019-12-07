package br.com.senna.aws_project01.repository;

import br.com.senna.aws_project01.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
