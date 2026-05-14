# Autode garaaži programm

**Kerdo Timak, Ain Tamm**

---

## Projekti kirjeldus

Projekti eesmärk oli luua programm, mis võimaldab hallata autosid garaažis.  
Kasutaja saab lisada autosid, neid vaadata, eemaldada ning kasutada (nt sõita, tankida ja remontida).

---

## Programmi üldine töö

Programm koosneb kahest põhiklassist:

- **Auto** - kirjeldab ühte autot ja selle omadusi
- **Garaaž** - haldab autode kogumit

Kasutaja saab:

- lisada uusi autosid garaaži
- vaadata garaažis olevaid autosid
- valida konkreetse auto
- sõita valitud autoga (kulutades kütust ja pärast sõitu on võimalus, et auto läheb katki)
- tankida autot
- parandada autot
- eemaldada auto garaažist

---

## Klasside kirjeldus

### MainApp

Peaklass, mis loob javafx graafilise keskonna ja realiseerib seal järgmiste klasside meetodeid.

---

### Auto

Hoiab ühe auto andmeid ja võimaldab sellega seotud tegevusi.

**Meetodid:**

- `soida(int km)`
    - suurendab läbisõitu
    - vähendab kütust
    - võib auto katki teha (juhuslikult)

- `tangi(double liitrid)`
    - lisab kütust paaki

- `remondi()`
    - parandab auto, kui see on katki

- `toString()`
    - tagastab auto info tekstina

---

### Garaaž

Haldab autode listi ja võimaldab nendega töötada.

**Meetodid:**

- `lisaAuto(Auto auto)`
    - lisab auto garaaži

- `kuvaNimekiri()`
    - kuvab kõik autod garaažis

- `valiAuto(int indeks)`
    - tagastab valitud auto info

- `eemaldaAuto(int indeks)`
    - eemaldab auto garaažist

---

### FailiHaldur

Tegeleb faili kirjutamisega ja seal andmete lugemisega.

**Meetodid:**

- `loeFailist(Garaaž garaaz)`
    - loeb failist andmed
    - loob Auto isendid
    - lisab Auto isendid klassi Garaaz

- `kirjutaFaili(Garaaž garaaz)`
    - Võtab Auto isendid Garaaz klassist ja kirjutab need faili andmed.txt.

---

## Projekti tegemise protsess

Esimene samm oli uue repositooriumi loomine, JOptionsPane kasutuse eemaldamine klassidest ja ülesannete jagamine.  
Teises etappis alustas üks liige peaklassi MainApp loomist, lõi klassi FailiHaldur ja valmis algne versiooni kasutajaliidesest.  
Teine liige arendas peaklassi edasi ja lisas sellele vajalikud komponendid ja funktionaalsuse.  
Kolmandas etapis tegid mõlemad liikmed teste ning täiendavaid muudatusi.  
Viimases etapis lõime rühmatöö kirjelduse ja esitasime töö.

---

## Tehisintellekti kasutamine

**Ain Tamm** - Kasutasin tehisintellekti vigade leidmiseks ja parimate lahenduse leidmiseks 
nagu näiteks ListView, millest ma ei olnud varem teadlik.

**Kerdo Timak** - 

---

## Liikmete panus

### Ain Tamm

- Peaklassi alustamine
- klassi FailiHaldur loomine

Ajakulu: 5h

---

### Kerdo Timak

- Peaklassi arendamine ja lõpetamine

Ajakulu: 5h

---

## Tekkinud probleemid

- -

---

## Hinnang töötulemusele

**Õnnestus hästi:**

- praktilise javafx kasutajaiidese loomine.
- faili lugemine ja kirjutamine.

**Vajab arendamist:**

- vigade käsitlemine (nt vale sisend)
- rohkem funktsioone (nt mitu garaaži)
- Ilusama kasutajaliidese loomine

---

## Testimine

Testisime tervet programmi korraga.  
Saadud tulemusest mõtlesime, mida ja kuidas muuta.