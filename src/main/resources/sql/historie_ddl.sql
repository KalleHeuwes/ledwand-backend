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

CREATE OR REPLACE VIEW SPIELER_VW AS 
SELECT nachname, vorname, kuerzel, status, Left(vorname, 1) || '. ' || nachname AS kuerzel_ev
FROM SPIELER
ORDER BY 1, 2;

CREATE OR REPLACE VIEW EV_BAELLE_GRP_VW AS
SELECT saison, Count(*) spielerbaelle_anz, Count(DISTINCT spieltag) spieltage_anz
    , Min(spieltag) spieltag_min, Max(spieltag) spieltag_max 
FROM EV_BAELLE GROUP BY saison;

CREATE OR REPLACE VIEW EV_BAELLE_SPIELER_VW AS
SELECT spieler, saison, Count(*) baelle_anz
    , Round(Avg(anz_baelle), 2) baelle_avg, Min(anz_baelle) baelle_min, Max(anz_baelle) baelle_max
FROM EV_BAELLE GROUP BY spieler, saison;

CREATE OR REPLACE VIEW EV_BAELLE_PIVOT_VW AS
SELECT 
    spieler, saison,
    SUM(CASE WHEN spieltag =  1 THEN anz_baelle ELSE null END) AS spieltag_01,
    SUM(CASE WHEN spieltag =  2 THEN anz_baelle ELSE null END) AS spieltag_02,
    SUM(CASE WHEN spieltag =  3 THEN anz_baelle ELSE null END) AS spieltag_03,
    SUM(CASE WHEN spieltag =  4 THEN anz_baelle ELSE null END) AS spieltag_04,
    SUM(CASE WHEN spieltag =  5 THEN anz_baelle ELSE null END) AS spieltag_05,
    SUM(CASE WHEN spieltag =  6 THEN anz_baelle ELSE null END) AS spieltag_06,
    SUM(CASE WHEN spieltag =  7 THEN anz_baelle ELSE null END) AS spieltag_07,
    SUM(CASE WHEN spieltag =  8 THEN anz_baelle ELSE null END) AS spieltag_08,
    SUM(CASE WHEN spieltag =  9 THEN anz_baelle ELSE null END) AS spieltag_09,
    SUM(CASE WHEN spieltag = 10 THEN anz_baelle ELSE null END) AS spieltag_10,
    SUM(CASE WHEN spieltag = 11 THEN anz_baelle ELSE null END) AS spieltag_11,
    SUM(CASE WHEN spieltag = 12 THEN anz_baelle ELSE null END) AS spieltag_12,
    SUM(CASE WHEN spieltag = 13 THEN anz_baelle ELSE null END) AS spieltag_13,
    SUM(CASE WHEN spieltag = 14 THEN anz_baelle ELSE null END) AS spieltag_14,
    SUM(CASE WHEN spieltag = 15 THEN anz_baelle ELSE null END) AS spieltag_15,
    SUM(CASE WHEN spieltag = 16 THEN anz_baelle ELSE null END) AS spieltag_16,
    SUM(CASE WHEN spieltag = 17 THEN anz_baelle ELSE null END) AS spieltag_17,
    SUM(CASE WHEN spieltag = 18 THEN anz_baelle ELSE null END) AS spieltag_18,
    SUM(CASE WHEN spieltag = 19 THEN anz_baelle ELSE null END) AS spieltag_19,
    SUM(CASE WHEN spieltag = 20 THEN anz_baelle ELSE null END) AS spieltag_20,
    SUM(CASE WHEN spieltag = 21 THEN anz_baelle ELSE null END) AS spieltag_21,
    SUM(CASE WHEN spieltag = 22 THEN anz_baelle ELSE null END) AS spieltag_22,
    SUM(CASE WHEN spieltag = 23 THEN anz_baelle ELSE null END) AS spieltag_23,
    SUM(CASE WHEN spieltag = 24 THEN anz_baelle ELSE null END) AS spieltag_24,
    SUM(CASE WHEN spieltag = 25 THEN anz_baelle ELSE null END) AS spieltag_25,
    SUM(CASE WHEN spieltag = 26 THEN anz_baelle ELSE null END) AS spieltag_26,
    SUM(CASE WHEN spieltag = 27 THEN anz_baelle ELSE null END) AS spieltag_27,
    SUM(CASE WHEN spieltag = 28 THEN anz_baelle ELSE null END) AS spieltag_28,
    SUM(CASE WHEN spieltag = 29 THEN anz_baelle ELSE null END) AS spieltag_29,
    SUM(CASE WHEN spieltag = 30 THEN anz_baelle ELSE null END) AS spieltag_30,
    SUM(CASE WHEN spieltag = 31 THEN anz_baelle ELSE null END) AS spieltag_31,
    SUM(CASE WHEN spieltag = 32 THEN anz_baelle ELSE null END) AS spieltag_32,
    SUM(CASE WHEN spieltag = 33 THEN anz_baelle ELSE null END) AS spieltag_33,
    SUM(CASE WHEN spieltag = 34 THEN anz_baelle ELSE null END) AS spieltag_34
FROM   ev_baelle
GROUP BY spieler, saison
ORDER BY 1, 2