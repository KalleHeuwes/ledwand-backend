-- Abschicken auf http://localhost:8080/h2-console; jdbc:h2:file:./data/fussballDB; sa; password

-- Abfrage für die Historie der Spieler Einsätze
-- Nutzt die View SPIELER_EINSATZ_VW
SELECT * FROM SPIELER_EINSATZ_VW 
WHERE wert IS NOT NULL AND wert <> '' 
ORDER BY saison DESC, spielnummer, gruppe, nachname, vorname