package com.ufcg.psoft.mercadofacil.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;


@SpringBootTest
class VolatilLoteRepositoryTest {


   @Autowired
   VolatilLoteRepository driver;


   Lote lote;
   Lote resultado;
   Produto produto;

   
   @BeforeEach
   void setup() {
       produto = Produto.builder()
               .id(1L)
               .nome("Produto Base")
               .codigoBarra("123456789")
               .fabricante("Fabricante Base")
               .preco(125.36)
               .build();
       lote = Lote.builder()
               .id(1L)
               .numeroDeItens(100)
               .produto(produto)
               .build();
   }


   @AfterEach
   void tearDown() {
       produto = null;
       driver.deleteAll();
   }


   @Test
   @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
   void salvarPrimeiroLote() {
       resultado = driver.save(lote);


       assertEquals(driver.findAll().size(),1);
       assertEquals(resultado.getId().longValue(), lote.getId().longValue());
       assertEquals(resultado.getProduto(), produto);
   }


   @Test
   @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados")
   void salvarSegundoLoteOuPosterior() {
       Produto produtoExtra = Produto.builder()
               .id(2L)
               .nome("Produto Extra")
               .codigoBarra("987654321")
               .fabricante("Fabricante Extra")
               .preco(125.36)
               .build();
       Lote loteExtra = Lote.builder()
               .id(2L)
               .numeroDeItens(100)
               .produto(produtoExtra)
               .build();
       driver.save(lote);


       resultado = driver.save(loteExtra);


       assertEquals(driver.findAll().size(),2);
       assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
       assertEquals(resultado.getProduto(), produtoExtra);


   }
   
   @Test
   @DisplayName("Achar Lotes via ID")
   void acharLotes() {
       Lote inserido = driver.save(lote);
       
       assertEquals(driver.find(1L), inserido);
       
       Produto new_produto = Produto.builder()
               .id(2L)
               .nome("Produto 2")
               .codigoBarra("5550123")
               .fabricante("Outro Fabricante")
               .preco(5.75)
               .build();
       Lote new_lote = Lote.builder()
               .id(2L)
               .numeroDeItens(50)
               .produto(new_produto)
               .build();
       
       driver.save(new_lote);
       
       assertEquals(driver.find(2L), new_lote);
       
   }
   
   @Test
   @DisplayName("Retornar a lista completa de Lotes")
   void acharTodosLotes() {
              
       Produto produto2 = Produto.builder()
               .id(2L)
               .nome("Produto 2")
               .codigoBarra("5550123")
               .fabricante("Outro Fabricante")
               .preco(5.75)
               .build();
       
       Produto produto3 = Produto.builder()
               .id(3L)
               .nome("Produto 3")
               .codigoBarra("2345678")
               .fabricante("Mais um Fabricante")
               .preco(0.5)
               .build();
       
       
       Lote lote2 = Lote.builder()
               .id(2L)
               .numeroDeItens(50)
               .produto(produto2)
               .build();
              
       Lote lote3 = Lote.builder()
               .id(3L)
               .numeroDeItens(55)
               .produto(produto3)
               .build();
       
       driver.save(lote);
       driver.save(lote2);
       driver.save(lote3);
       
       List<Lote> lista = driver.findAll();
       assertEquals(lista.size(), 3);
       
       assertEquals(lista.get(0), lote);
       assertEquals(lista.get(1), lote2);
       assertEquals(lista.get(2), lote3);
       
   }
   
   @Test
   @DisplayName("Atualizar o repositório")
   void updateRepository() {
              
       Produto produto2 = Produto.builder()
               .id(2L)
               .nome("Produto 2")
               .codigoBarra("5550123")
               .fabricante("Outro Fabricante")
               .preco(5.75)
               .build();
       
       Produto produto3 = Produto.builder()
               .id(3L)
               .nome("Produto 3")
               .codigoBarra("2345678")
               .fabricante("Mais um Fabricante")
               .preco(0.5)
               .build();
       
       Lote lote2 = Lote.builder()
               .id(2L)
               .numeroDeItens(50)
               .produto(produto2)
               .build();
              
       Lote lote3 = Lote.builder()
               .id(3L)
               .numeroDeItens(55)
               .produto(produto3)
               .build();
       
       driver.save(lote);
       driver.save(lote2);
       driver.save(lote3);
              
       Lote retorno = driver.update(lote3);
       
       assertNotNull(retorno);
       assertEquals(driver.findAll().size(), 1);
       assertEquals(lote3, retorno);
       assertEquals(driver.find(3L), lote3);
       
   }

   @Test
   @DisplayName("Deletar um Lote do Repositório")
   void deletarLote() {
                     
       Produto produto2 = Produto.builder()
               .id(2L)
               .nome("Produto 2")
               .codigoBarra("5550123")
               .fabricante("Outro Fabricante")
               .preco(5.75)
               .build();       
       
       Lote lote2 = Lote.builder()
               .id(2L)
               .numeroDeItens(50)
               .produto(produto2)
               .build();
       
       Produto produto3 = Produto.builder()
               .id(3L)
               .nome("Produto 3")
               .codigoBarra("2345678")
               .fabricante("Mais um Fabricante")
               .preco(0.5)
               .build();
                     
       Lote lote3 = Lote.builder()
               .id(3L)
               .numeroDeItens(55)
               .produto(produto3)
               .build();
       
       driver.save(lote);
       driver.save(lote2);
       driver.save(lote3);
       
       driver.delete(lote);
       assertEquals(driver.findAll().size(), 2);
        
   }
   
   @Test
   @DisplayName("Deletar todos os Lotes do Repositório")
   void deletarTodosLotes() {
                     
       Produto produto2 = Produto.builder()
               .id(2L)
               .nome("Produto 2")
               .codigoBarra("5550123")
               .fabricante("Outro Fabricante")
               .preco(5.75)
               .build();       
       
       Lote lote2 = Lote.builder()
               .id(2L)
               .numeroDeItens(50)
               .produto(produto2)
               .build();
       
       Produto produto3 = Produto.builder()
               .id(3L)
               .nome("Produto 3")
               .codigoBarra("2345678")
               .fabricante("Mais um Fabricante")
               .preco(0.5)
               .build();
                     
       Lote lote3 = Lote.builder()
               .id(3L)
               .numeroDeItens(55)
               .produto(produto3)
               .build();
       
       driver.save(lote);
       driver.save(lote2);
       driver.save(lote3);
       
       driver.deleteAll();
       assertEquals(driver.findAll().size(), 0);
        
   }

}

