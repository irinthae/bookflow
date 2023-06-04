insert into library(id, name, address)
values(1, 'Timeless Archives', '1050 Wien, Spengergasse 20');
insert into library(id, name, address)
values(2, 'Mythos Archives', '1050 Wien, Spengergasse 20');
insert into library(id, name, address)
values(3, 'Stellar Archives', '1050 Wien, Spengergasse 20');

insert into book(id, title, author, language1, language2, year_of_publication, pages, category, content, fk_library)
values(1, 'Metamorphosen', ' Michael von Albrecht (Herausgeber, Übersetzer), Ovid (Autor)', 'LATIN', 'GERMAN',
       1997, 997, 'CLASSICS', 'Für das Bild, das man sich von der antiken Religion machte, ' ||
                              'war lange Zeit Ovids Gestaltung des Mythos bestimmend, die Loslösung der Mythen von ' ||
                              'ihrer kultischen Verankerung, ihre psychologische Durchdringung und die Akzentuierung ihrer ' ||
                              'menschlichen Aspekte... Ovid hat den Mythos auch für spätere Epochen leicht assimilierbar und ' ||
                              'übertragbar gemacht. So konnte auch in christlicher Zeit und in der modernen Welt ein fester ' ||
                              'Bilderschatz und so etwas wie eine Weltsprache der Dichtung und der Kunst fortleben.',
       1);
insert into book(id, title, author, language1, year_of_publication, pages, category, content, fk_library)
values(2, 'Siddharta', 'Hermann Hesse', 'GERMAN',
       2016, 128, 'CLASSICS', 'Siddhartha, die Legende von der Selbstbefreiung eines jungen Menschen aus familiärer ' ||
                              'und gesellschaftlicher Fremdbestimmung zu einem selbständigen Leben, zeigt, dass ' ||
                              'Erkenntnis nicht durch Lehren zu vermitteln ist, sondern nur durch eigene Erfahrung ' ||
                              'erworben werden kann.',
       1);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(3, 'The Way of Kings', 'Brandon Sanderson', 'ENGLISH',
       2010, 1007, 'FANTASY', 2);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(4, 'Words of Radiance', 'Brandon Sanderson', 'ENGLISH',
       2014, 1087, 'FANTASY', 2);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(5, 'Oathbringer', 'Brandon Sanderson', 'ENGLISH',
       2017, 1233, 'FANTASY', 2);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(6, 'Rythm of War', 'Brandon Sanderson', 'ENGLISH',
       2020, 1232, 'FANTASY', 2);

insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(7, 'The Dispossessed', 'Ursula K. Le Guin', 'ENGLISH',
       1994, 400, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(8, 'The Left Hand of Darkness', 'Ursula K. Le Guin', 'ENGLISH',
       1987, 286, 'SCIENCE_FICTION', 3);
insert into book(id, title, author, language1, year_of_publication, pages, category, fk_library)
values(9, 'Leviathan Wakes', 'James S. A. Corey', 'ENGLISH',
       2011, 561, 'SCIENCE_FICTION', 3);