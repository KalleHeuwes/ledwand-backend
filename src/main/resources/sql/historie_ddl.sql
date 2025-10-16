-- Abschicken auf http://localhost:8080/h2-console; jdbc:h2:file:./data/fussballDB; sa; password

/* WIRD NICHT MEHR GEBRAUCHT
-- View zum Einsatz der Spieler in den Spielen, Enthält die Gruppierung in Startelf, Bank und Kader
CREATE OR REPLACE VIEW SPIELER_EINSATZ_VW AS 
SELECT s.saison, e.spielnummer, s.nachname, s.vorname , e.wert AS einsatz
,   CASE
        WHEN Left(wert, 1)  = '1'       THEN '01_Startelf'
        WHEN wert           = 'Voll'    THEN '01_Startelf'
        WHEN wert           = 'Kader'   THEN '03_Kader'
                                        ELSE '02_Bank'
    END AS Gruppe
FROM SPIELEREINSAETZE s
INNER JOIN SPIELER_EINSATZ_SPIELE e ON s.ID = e.spieler_einsatz_id;
*/