package services;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service
public final class DateService {

    /**
     *
     * @return
     */
    public static String getTempsRestant(long millisRestantes)
    {
        // Verification si terminee ou non
        long secondes = TimeUnit.MILLISECONDS.toSeconds(millisRestantes);
        if(secondes <= 0) return "Terminé";

        // Recuperation du temps restant et affichage personnalisée selon la plus grande duree
        long jours = TimeUnit.SECONDS.toDays(secondes);

        // Affichage annee:mois
        long annee = jours/365;
        if(annee > 0)
        {
            long mois = (jours%365)/30;
            if(annee>1 && mois>1) return String.format("%d ans %d mois", annee, mois);
            if(annee>1)           return String.format("%d ans", annee);
            if(mois==1)           return String.format("1 an %d mois", mois);
            return String.format("1 an");
        }

        // Affichage mois:semaines
        long mois = jours/30;
        if(mois > 0)
        {
            long semaines = (jours%30)/7;
            if(semaines>1)  return String.format("%d mois %d semaines", mois, semaines);
            if(semaines==1) return String.format("%d mois 1 semaine", mois);
            return String.format("1 mois");
        }

        // Affichage semaines:jours
        long semaines = jours/7;
        if(semaines > 0)
        {
            long reste = jours%7;
            if(semaines>1 && reste>1)  return String.format("%d semaines %d jours", semaines, reste);
            if(semaines>1 && reste==1) return String.format("%d semaines 1 jour", semaines);
            if(semaines>1)             return String.format("%d semaines", semaines);
            if(reste>1)                return String.format("1 semaine %d jours", reste);
            if(reste==1)               return String.format("1 semaine 1 heure");
            return String.format("1 semaine");
        }

        // Affichage jours:heures
        if(jours > 0)
        {
            long heures = (secondes/3600)%24;
            if(jours>1 && heures>1)  return String.format("%d jours %d heures", jours, heures);
            if(jours>1 && heures==1) return String.format("%d jours 1 heure", jours);
            if(jours>1)              return String.format("%d jours", jours);
            if(heures>1)             return String.format("1 jour %d heures", heures);
            if(heures==1)            return String.format("1 jour 1 heure");
            return String.format("1 jour");
        }

        // Affichage en heure:minutes:secondes
        long heures  = (secondes/3600)%24;
        long minutes = (secondes/60)%60;
        secondes     = secondes%60;
        return String.format("%02d:%02d:%02d", heures, minutes, secondes);
    }

    /**
     * TODO doc
     * @param timestamp
     * @return
     */
    public static String getDateEuropeenne(long timestamp)
    {
        LocalDateTime date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
        );

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy à HH:mm");
        return date.format(format);
    }
}
