@Entity
public class Categoria {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @NotBlank(message = "O nome da categoria é obrigatório.")
      @Column(nullable = false, unique = true)
      private String nome;

      @OneToMany(mappedBy = "categoria")
      private List<Produto> produtos;


      // getters e setters
      public Long getId() { 
          return id; 
      }
      public void setId(Long id) { 
          this.id = id; 
      }

      public String getNome() { 
          return nome; 
      }
      public void setNome(String nome) { 
          this.nome = nome; 
      }

      public List<Produto> getProdutos() { 
          return produtos; 
      }
      public void setProdutos(List<Produto> produtos) { 
          this.produtos = produtos; 
      }

}
