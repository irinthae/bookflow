insert into library(id, name, address)
values(1, 'Timeless Archives', '1050 Wien, Spengergasse 20');
insert into library(id, name, address)
values(2, 'Mythos Archives', '1050 Wien, Spengergasse 20');
insert into library(id, name, address)
values(3, 'Stellar Archives', '1050 Wien, Spengergasse 20');


insert into book(id, title, author, language1, year_of_publication, pages, category, content, fk_library)
values(1, 'Dr. Jekyll und Mr. Hyde', 'Robert Louis Stevenson', 'ENGLISH',
       2005, 128, 'CLASSICS', 'The Strange Case of Dr. Jekyll & Mr. Hyde is a gothic novella by Scottish author, Robert Louis Stevenson. ' ||
                              'It was originally published in 1886.
                                The story is about a legal practitioner in London, named Gabriel John Utterson, who investigates bizarre incidents ' ||
                              'that occur between his old friend, Dr. Henry Jekyll, and the evil Edward Hyde. The concept of one person having two personas, ' ||
                              'one outwardly good and the other sometimes shockingly evil inside, is reflected in this literary classic.',
       1);
insert into book(id, title, author, language1, year_of_publication, pages, category, content, fk_library)
values(2, 'The Picture of Dorian Gray', 'Oscar Wilde', 'ENGLISH',
       2005, 128, 'CLASSICS', 'Dorian Gray is the subject of a full-length portrait in oil by Basil Hallward, an artist impressed and infatuated by Dorians beauty;
                                he believes that Dorians beauty is responsible for the new mood in his art as a painter. ' ||
                                'Through Basil, Dorian meets Lord Henry Wotton, and he soon is enthralled by the aristocrats hedonistic world view: that beauty and sensual ' ||
                              'fulfilment are the only things worth pursuing in life.' ||
                              'Newly understanding that his beauty will fade, Dorian expresses the desire to sell his soul, ' ||
                              'to ensure that the picture, rather than he, will age and fade. The wish is granted, and Dorian pursues a ' ||
                              'libertine life of varied amoral experiences while staying young and beautiful; all the while, his portrait ages and records every sin.',
       1);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(3, 'Das Urteil', 'Franz Kafka', 'GERMAN',
       1989, 192, 'CLASSICS',
       1);
insert into book(id, title, author, language1, language2, year_of_publication, pages, category, content, fk_library)
values(4, 'Metamorphosen', ' Michael von Albrecht (Herausgeber, Übersetzer), Ovid (Autor)', 'LATIN', 'GERMAN',
       1997, 997, 'CLASSICS', 'Für das Bild, das man sich von der antiken Religion machte, ' ||
                              'war lange Zeit Ovids Gestaltung des Mythos bestimmend, die Loslösung der Mythen von ' ||
                              'ihrer kultischen Verankerung, ihre psychologische Durchdringung und die Akzentuierung ihrer ' ||
                              'menschlichen Aspekte... Ovid hat den Mythos auch für spätere Epochen leicht assimilierbar und ' ||
                              'übertragbar gemacht. So konnte auch in christlicher Zeit und in der modernen Welt ein fester ' ||
                              'Bilderschatz und so etwas wie eine Weltsprache der Dichtung und der Kunst fortleben.',
       1);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(5, 'Siddharta', 'Hermann Hesse', 'GERMAN',
       2016, 128, 'CLASSICS', 1);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(6, 'Faust: Der Tragödie Erster und Zweiter Teil', 'Johann Wolfgang Goethe', 'GERMAN',
       2020, 356, 'CLASSICS', 1);


insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(7, 'The Way of Kings', 'Brandon Sanderson', 'ENGLISH',
       2010, 1007, 'FANTASY', 2);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(8, 'Words of Radiance', 'Brandon Sanderson', 'ENGLISH',
       2014, 1087, 'FANTASY', 2);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(9, 'Oathbringer', 'Brandon Sanderson', 'ENGLISH',
       2017, 1233, 'FANTASY', 2);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(10, 'Rythm of War', 'Brandon Sanderson', 'ENGLISH',
       2020, 1232, 'FANTASY', 2);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(11, 'The Name of the Wind', 'Patrick Rothfuss', 'ENGLISH',
       2010, 676, 'FANTASY', 2);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(12, 'The Wise Man''s Fear', 'Patrick Rothfuss', 'ENGLISH',
       2011, 1007, 'FANTASY', 2);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(13, 'Die Nebel von Avalon', 'Marion Zimmer Bradley', 'GERMAN',
       1987, 1118, 'FANTASY', 2);


insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(14, 'The Dispossessed', 'Ursula K. Le Guin', 'ENGLISH',
       1994, 400, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(15, 'The Left Hand of Darkness', 'Ursula K. Le Guin', 'ENGLISH',
       1987, 286, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(16, 'The Lathe Of Heaven', 'Ursula K. Le Guin', 'ENGLISH',
       2001, 192, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(17, 'The Word for World is Forest', 'Ursula K. Le Guin', 'ENGLISH',
       2015, 144, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(18, 'Do Androids Dream of Electric Sheep?', 'Philip K. Dick', 'ENGLISH',
       2009, 208, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(19, 'The Forever War: Forever War Book 1', 'Joe Haldeman', 'ENGLISH',
       1980, 256, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(20, 'Leviathan Wakes', 'James S. A. Corey', 'ENGLISH',
       2011, 561, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(21, 'Caliban''s War', 'James S. A. Corey', 'ENGLISH',
       2013, 624, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(22, ' Abaddon''s Gate', 'James S. A. Corey', 'ENGLISH',
       2014, 560, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(23, 'Cibola Burn', 'James S. A. Corey', 'ENGLISH',
       2015, 560, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(24, 'Nemesis Games', 'James S. A. Corey', 'ENGLISH',
       2016, 560, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(25, 'Babylon''s Ashes', 'James S. A. Corey', 'ENGLISH',
       2017, 560, 'SCIENCE_FICTION', 3);