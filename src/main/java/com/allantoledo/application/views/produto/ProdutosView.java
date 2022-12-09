package com.allantoledo.application.views.produto;

import com.allantoledo.application.data.database.CategoriaProdutoRepository;
import com.allantoledo.application.data.database.ProdutoRepository;
import com.allantoledo.application.data.database.Repository;
import com.allantoledo.application.data.entity.CategoriaProduto;
import com.allantoledo.application.data.entity.Produto;
import com.allantoledo.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Produtos")
@Route(value = "produto", layout = MainLayout.class)
public class ProdutosView extends VerticalLayout {

    private Repository<Produto> produtoRepository = new ProdutoRepository();
    private Repository<CategoriaProduto> categoriaProdutoRepository = new CategoriaProdutoRepository();

    private IntegerField idField;
    private ComboBox<CategoriaProduto> categoriaField;

    private TextField nomeField;
    private BigDecimalField valorField;
    private BigDecimalField custoField;

    private Button salvarButton;
    private Button cancelarButton;


    private Grid<Produto> grid = new Grid<>(Produto.class, false);

    public ProdutosView() {
        idField = new IntegerField("Id");
        idField.setReadOnly(true);
        categoriaField = new ComboBox<>("Categoria");
        categoriaField.setItems(categoriaProdutoRepository.getAll());
        categoriaField.setItemLabelGenerator(CategoriaProduto::getCategoria);

        nomeField = new TextField("Nome");
        valorField = new BigDecimalField("Valor");
        custoField = new BigDecimalField("Custo");
        Div real1Preffix = new Div();
        Div real2Preffix = new Div();
        real1Preffix.setText("R$");
        real2Preffix.setText("R$");
        valorField.setPrefixComponent(real1Preffix);
        custoField.setPrefixComponent(real2Preffix);

        grid.addColumn(Produto::getNome).setHeader("Nome");
        grid.addColumn(Produto::getCategoriaProduto).setHeader("Categoria");
        grid.addColumn(Produto::getValor).setHeader("Valor");
        grid.addColumn(Produto::getCusto).setHeader("Custo");
        grid.addComponentColumn(produto -> new Button("Remover", click -> {
            produtoRepository.delete(produto);
            grid.setItems(produtoRepository.getAll());
        }));
        grid.addComponentColumn(produto -> new Button("Editar", click -> {
            idField.setValue(produto.getId());
            categoriaField.setValue(produto.getCategoriaProduto());
            nomeField.setValue(produto.getNome());
            valorField.setValue(produto.getValor());
            custoField.setValue(produto.getCusto());
        }));

        salvarButton = new Button("Salvar", click -> {
            Produto produto = new Produto(
                    categoriaField.getValue(),
                    nomeField.getValue(),
                    valorField.getValue(),
                    custoField.getValue()
            );
            if(idField.getValue() != null) {
                produto.setIdProduto(idField.getValue());
                produtoRepository.update(produto);
            } else {
                produtoRepository.save(produto);
            }
            cancelarButton.click();
            grid.setItems(produtoRepository.getAll());
        });

        cancelarButton = new Button("Cancelar", click -> {
            idField.clear();
            categoriaField.clear();
            nomeField.clear();
            valorField.clear();
            custoField.clear();
        });

        HorizontalLayout form = new HorizontalLayout();
        form.add(salvarButton, cancelarButton);

        grid.setItems(produtoRepository.getAll());
        add(idField, categoriaField, nomeField,valorField, custoField, form, grid);
    }
}
