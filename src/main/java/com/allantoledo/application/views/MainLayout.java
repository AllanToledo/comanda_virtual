package com.allantoledo.application.views;


import com.allantoledo.application.components.appnav.AppNav;
import com.allantoledo.application.components.appnav.AppNavItem;
import com.allantoledo.application.views.categoria.CategoriaProdutoView;
import com.allantoledo.application.views.inicio.MesasView;
import com.allantoledo.application.views.mesa.MesaView;
import com.allantoledo.application.views.pagamento.FormaPagamentoView;
import com.allantoledo.application.views.produto.ProdutosView;
import com.allantoledo.application.views.relatorio.RelatorioView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Comanda Virtual");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

        nav.addItem(new AppNavItem("Mesas", MesasView.class, "la la-globe"));
        nav.addItem(new AppNavItem("Categorias", CategoriaProdutoView.class, "la la-globe"));
        nav.addItem(new AppNavItem("Produtos", ProdutosView.class, "la la-globe"));
        nav.addItem(new AppNavItem("Config. Mesa", MesaView.class, "la la-globe"));
        nav.addItem(new AppNavItem("Formas de Pagamento", FormaPagamentoView.class, "la la-globe"));
        nav.addItem(new AppNavItem("Relatórios", RelatorioView.class, "la la-globe"));
        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
