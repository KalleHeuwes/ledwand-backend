-- Abschicken auf http://localhost:8080/h2-console; jdbc:h2:file:./data/fussballDB; sa; password

CREATE OR REPLACE VIEW SPIELEREINSAETZE_VW AS 
SELECT E.*, S.DATUM, S.GEGNER, S.ERGEBNIS, S.GESCHOSSEN, S.KASSIERT FROM SPIELEREINSAETZE E
INNER JOIN SPIELTAGE S ON E.SAISON = S.SAISON AND E.SPIEL = S.SPIELTAG;

-- Anzahl Saisons, erste und letzte je Tabelle
CREATE OR REPLACE VIEW STATUS_VW AS 
SELECT 'ABSCHLUSSTABELLEN' kategorie, COUNT(DISTINCT saison) saison_anzahl, MIN(saison) saison_min, MAX(saison) saison_max 
FROM ABSCHLUSSTABELLENEINTRAG UNION ALL
SELECT 'SAISONS' kategorie, COUNT(DISTINCT saison), MIN(saison) saison_min, MAX(saison) saison_max 
FROM SAISONEINTRAG UNION ALL
SELECT 'SPIELEREINSAETZE' kategorie, COUNT(DISTINCT saison), MIN(saison) saison_min, MAX(saison) saison_max 
FROM SPIELEREINSAETZE UNION ALL
SELECT 'SPIELTAGE' kategorie, COUNT(DISTINCT saison), MIN(saison) saison_min, MAX(saison) saison_max 
FROM SPIELTAGE;

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