-- Abschicken auf http://localhost:8080/h2-console; jdbc:h2:file:./data/fussballDB; sa; password

CREATE OR REPLACE VIEW SPIELERPERFORMANCE_VW AS 
SELECT min(e.id) id_min, e.nachname, e.vorname, e.saison, max(s.liga) liga
    , Count(e.*) spiele_spieler, s.spiele spiele_team, 100 * Count(e.*) / s.spiele AS spiele_anteil
    , Sum(e.spielminuten) spielminuten_spieler, Cast(s.spiele * 90 AS INTEGER) AS spielminuten_team
    , 100 * Sum(e.spielminuten) / (s.spiele * 90) AS spielminuten_anteil
    , Sum(e.punkte) punkte_spieler, s.punkte punkte_team, 100 * Sum(e.punkte) / s.punkte AS punkte_anteil
    , min(e.spiel) spiel_min, max(e.spiel) spiel_max   
    , s.bemerkungen 
FROM        SPIELEREINSAETZE_VW E
INNER JOIN  SAISONEINTRAG       S ON E.SAISON = S.SAISON
GROUP BY e.nachname, e.vorname, e.saison, s.spiele, s.punkte
ORDER BY e.nachname, e.vorname, e.saison

CREATE OR REPLACE VIEW SPIELEREINSAETZE_VW AS 
SELECT E.id, E.nachname, E.vorname, E.saison, E.spiel, E.einsatz, E.gruppe
    ,   CASE 
            WHEN E.einsatz = 'Voll' THEN 90
            WHEN E.einsatz = 'Kader' THEN 0
            WHEN E.einsatz LIKE '%-%' THEN 
                CAST(SUBSTRING(E.einsatz, POSITION('-', E.einsatz) + 1) AS INT) -
                CAST(SUBSTRING(E.einsatz, 1, POSITION('-', E.einsatz) - 1) AS INT) + 1
            ELSE 0
        END AS SPIELMINUTEN
    , S.DATUM, S.GEGNER, S.ERGEBNIS, S.punkte, S.heim_oder_auswaerts HA, S.GESCHOSSEN, S.KASSIERT
FROM        SPIELEREINSAETZE    E
INNER JOIN  SPIELTAGE           S ON E.SAISON = S.SAISON AND E.SPIEL = S.SPIELTAG;

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

drop table spielereinsaetze;
create table spielereinsaetze (ID integer, SAISON varchar(20), SPIEL integer, NACHNAME varchar(50), VORNAME varchar(50), EINSATZ varchar(20), GRUPPE varchar(20));

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