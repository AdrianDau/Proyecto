package es.adrian.views.filters;

import es.adrian.models.Ticket;
import es.adrian.utils.DateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class FilterDate {
    private ObservableList<Ticket> listaFechas;
    private ObservableList<Ticket> listaFiltrada;

    public FilterDate(ObservableList<Ticket> listaFechas) {
        this.listaFechas = listaFechas;
        listaFiltrada = FXCollections.observableArrayList();
    }

    public ObservableList<Ticket> filtrarDate(LocalDate filterDateInicio, LocalDate filterDateFinal)
    {
        System.out.println(filterDateInicio + "-------" + filterDateFinal);
        if (filterDateInicio!=null && filterDateFinal!=null)
        {
            listaFiltrada.clear();
            for (Ticket ticket : listaFechas)
            {

                if ((DateUtils.fromDateToLocalDate(ticket.getFecha()).isAfter(filterDateInicio) || DateUtils.fromDateToLocalDate(ticket.getFecha()).isEqual(filterDateInicio))
                        && (DateUtils.fromDateToLocalDate(ticket.getFecha()).isBefore(filterDateFinal) || DateUtils.fromDateToLocalDate(ticket.getFecha()).isEqual(filterDateFinal)))
                    listaFiltrada.add(ticket);
            }
            return listaFiltrada;
        }
        else
        {
            return listaFechas;
        }

    }
}
