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
    , S.DATUM, S.GEGNER, S.ERGEBNIS, S.punkte, S.HA, S.GESCHOSSEN, S.KASSIERT
FROM        KADER    E
INNER JOIN  SPIELTAGE           S ON E.SAISON = S.SAISON AND E.SPIEL = S.SPIELTAG;

CREATE OR REPLACE VIEW PERFORMANCEVERGLEICH_VW AS 
SELECT p.saison, p.liga, s.spiele
, Count(*) spieler_anzahl
, Min(spiele_spieler) spiele_min, Max(spiele_spieler) spiele_max, Round(Avg(spiele_spieler)) spiele_avg
, Min(spielminuten_spieler) minuten_min, Max(spielminuten_spieler) minuten_max, Round(Avg(spielminuten_spieler)) minuten_avg
, Min(punkte_spieler) punkte_min, Max(punkte_spieler) punkte_max, Round(Avg(punkte_spieler)) punkte_avg
FROM SPIELERPERFORMANCE_VW p
LEFT JOIN SAISONS s ON p.saison = s.saison
GROUP BY p.saison, p.liga

-- Anzahl Saisons, erste und letzte je Tabelle
CREATE OR REPLACE VIEW STATUS_VW AS 
SELECT 'ABSCHLUSSTABELLEN' kategorie, COUNT(DISTINCT saison) saison_anzahl, MIN(saison) saison_min, MAX(saison) saison_max 
FROM ABSCHLUSSTABELLEN UNION ALL
SELECT 'SAISONS' kategorie, COUNT(DISTINCT saison), MIN(saison) saison_min, MAX(saison) saison_max 
FROM SAISONS UNION ALL
SELECT 'KADER' kategorie, COUNT(DISTINCT saison), MIN(saison) saison_min, MAX(saison) saison_max 
FROM KADER UNION ALL
SELECT 'SPIELTAGE' kategorie, COUNT(DISTINCT saison), MIN(saison) saison_min, MAX(saison) saison_max 
FROM SPIELTAGE;

drop table spielereinsaetze;
create table spielereinsaetze (ID integer, SAISON varchar(20), SPIEL integer, NACHNAME varchar(50), VORNAME varchar(50), EINSATZ varchar(20), GRUPPE varchar(20));

CREATE OR REPLACE VIEW SPIELER_METADATEN_VW AS 
SELECT nachname, vorname, nachname || ',' || Left(vorname, 1) kuerzel, 'A' status 
FROM KADER GROUP BY nachname, vorname;

CREATE OR REPLACE VIEW SPIELE_UEBERSICHT_VW AS 
SELECT saison, min(spieltag), max(spieltag), count(*) 
FROM SPIELTAGE 
GROUP BY saison ORDER BY 1;