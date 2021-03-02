package il.ac.shenkar.View;


import il.ac.shenkar.Model.CostItem;
import il.ac.shenkar.ViewModel.IViewModel;

import java.util.List;

public interface IView {

    //will be implemented after the ViewModel implementation


    //public void displayPieChart();

    public void displayCostItemTable(List<CostItem> cs);
    public void displayCategoriesChart(String[] catNames, double[] sums);
    public void displayCategoriesSelect(String[] catNames);
    public void displayCurrenciesSelect(String[] currencies);

    public void showMessage(String message);
    public void setViewModel(IViewModel vm);

}