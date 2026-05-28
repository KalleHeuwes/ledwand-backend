-- Abschicken auf http://localhost:8080/h2-console; jdbc:h2:file:./data/fussballDB; sa; password

-- Abfrage für die Historie der Spieler Einsätze
-- Nutzt die View SPIELER_EINSATZ_VW
SELECT * FROM SPIELER_EINSATZ_VW 
WHERE wert IS NOT NULL AND wert <> '' 
ORDER BY saison DESC, spielnummer, gruppe, nachname, vorname;

-- Notenbeste Spieler einer Saison
SELECT * FROM EV_BAELLE_SPIELER_VW 
WHERE saison = '2025/26' ORDER BY baelle_avg DESC;