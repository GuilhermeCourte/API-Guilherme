-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into book (id, titulo, autor, editora, anoLancamento, estaDisponivel) values(1, 'Aventuras na Vila do Sol', 'Ana Clara', 'Luz do Saber', 2022, true);
insert into book (id, titulo, autor, editora, anoLancamento, estaDisponivel) values(2, 'O Enigma do Farol', 'Dr. Alistair Finch', 'Páginas Antigas', 2018, true);
insert into book (id, titulo, autor, editora, anoLancamento, estaDisponivel) values(3, 'Crônicas de Orion', 'Helena Valois', 'Galáxia Editorial', 2024, false);
insert into book (id, titulo, autor, editora, anoLancamento, estaDisponivel) values(4, 'O Jardim Secreto de Maria', 'Julia Santos', 'Florescer Livros', 2021, false);
alter sequence book_seq restart with 5;
insert into BiografiaDiretor (textoCompleto, resumo, premiosRecebidos) values(
                                                                                 'Tim Burton é um diretor, produtor e roteirista americano conhecido por seu estilo gótico e excêntrico. Ele começou sua carreira como animador na Disney. Suas obras frequentemente exploram temas de solidão, individualidade e o estranho, com uma estética visual marcante e sombria. É um dos diretores mais reconhecidos de sua geração.',
                                                                                 'Diretor de cinema com estilo gótico e excêntrico',
                                                                                 'Globo de Ouro, BAFTA, indicações ao Oscar'
                                                                             );

insert into BiografiaDiretor (textoCompleto, resumo, premiosRecebidos) values(
                                                                                 'Wes Anderson é um diretor americano com um estilo visual distintivo, caracterizado por simetria, cores vibrantes, planos detalhados e um senso de humor peculiar. Ele é conhecido por suas narrativas sobre famílias disfuncionais e personagens peculiares. Suas obras são imediatamente reconhecíveis.',
                                                                                 'Diretor com estilo visual simétrico e colorido',
                                                                                 'Urso de Prata (Berlim), BAFTA, indicações ao Oscar'
                                                                             );

insert into BiografiaDiretor (textoCompleto, resumo, premiosRecebidos) values(
                                                                                 'Denis Villeneuve é um diretor canadense aclamado pela crítica por seus filmes de ficção científica e suspense. Suas obras são conhecidas por sua atmosfera densa, cinematografia imersiva e temas complexos, como identidade, tempo e humanidade. Ele se tornou uma das vozes mais importantes do cinema de gênero contemporâneo.',
                                                                                 'Diretor de ficção científica e suspense com obras aclamadas',
                                                                                 'Prêmio do Sindicato dos Diretores da América, indicações ao Oscar'
                                                                             );

insert into BiografiaDiretor (textoCompleto, resumo, premiosRecebidos) values(
                                                                                 'Greta Gerwig é uma diretora e atriz americana que ganhou destaque por suas narrativas sobre a jornada de crescimento e autodescoberta feminina. Suas obras são elogiadas pela autenticidade, diálogos inteligentes e personagens complexos. Ela é uma das diretoras mais influentes da atualidade.',
                                                                                 'Diretora e atriz com foco em narrativas femininas',
                                                                                 'Indicações ao Oscar (Melhor Direção e Roteiro)'
                                                                             );

insert into diretor (nome, nascimento, nacionalidade, biografia_id) values('Tim Burton', '1958-08-25', 'Americana', 1);
insert into diretor (nome, nascimento, nacionalidade, biografia_id) values('Wes Anderson', '1969-05-01', 'Americana', 2);
insert into diretor (nome, nascimento, nacionalidade, biografia_id) values('Denis Villeneuve', '1967-10-03', 'Canadense', 3);
insert into diretor (nome, nascimento, nacionalidade, biografia_id) values('Greta Gerwig', '1983-08-04', 'Americana', 4);

insert into genero (nome, descricao) values('Ficção Científica', 'Histórias com tecnologia avançada, exploração espacial ou futuros distópicos');
insert into genero (nome, descricao) values('Comédia', 'Filmes que buscam provocar riso e diversão');
insert into genero (nome, descricao) values('Suspense', 'Obras que constroem tensão e mistério para a audiência');
insert into genero (nome, descricao) values('Fantasia Sombria', 'Mistura de fantasia com elementos góticos e de horror');
insert into genero (nome, descricao) values('Aventura', 'Jornadas épicas, descobertas e desafios pessoais');
insert into genero (nome, descricao) values('Drama', 'Narrativas emocionais profundas sobre a condição humana');

insert into filme (titulo, sinopse, anoLancamento, nota, idadeIndicativa, diretor_id, status) values(
                                                                                                'Duna',
                                                                                                'O herdeiro de uma família nobre, Paul Atreides, é incumbido de proteger o planeta mais valioso da galáxia de forças malignas. O planeta é a única fonte de "especiaria", uma droga que prolonga a vida humana.',
                                                                                                2021, 9.1, 14, 3, 'EM_CARTAZ'
                                                                                            );

insert into filme (titulo, sinopse, anoLancamento, nota, idadeIndicativa, diretor_id, status) values(
                                                                                                'Blade Runner 2049',
                                                                                                'Um jovem "blade runner" descobre um segredo enterrado há muito tempo que tem o potencial de mergulhar o que resta da sociedade no caos.',
                                                                                                2017, 8.8, 16, 3, 'FORA_DE_CARTAZ'
                                                                                            );

insert into filme (titulo, sinopse, anoLancamento, nota, idadeIndicativa, diretor_id, status) values(
                                                                                                'Beetlejuice',
                                                                                                'Um casal recém-falecido contrata um bio-exorcista para assustar os novos e irritantes moradores de sua casa.',
                                                                                                1988, 8.5, 12, 1, 'FORA_DE_CARTAZ'
                                                                                            );

insert into filme (titulo, sinopse, anoLancamento, nota, idadeIndicativa, diretor_id, status) values(
                                                                                                'O Fantástico Sr. Raposo',
                                                                                                'Um raposo, que deixou a vida de ladrão para se dedicar à família, volta aos velhos hábitos e coloca em risco a vida de todos ao seu redor.',
                                                                                                2009, 8.2, 0, 2, 'FORA_DE_CARTAZ'
                                                                                            );

insert into filme (titulo, sinopse, anoLancamento, nota, idadeIndicativa, diretor_id, status) values(
                                                                                                'Lady Bird: A Hora de Voar',
                                                                                                'Uma estudante do último ano do ensino médio navega em um relacionamento tumultuado com sua mãe enquanto sonha em se mudar para uma cidade grande para a faculdade.',
                                                                                                2017, 8.6, 14, 4, 'FORA_DE_CARTAZ'
                                                                                            );

insert into filme (titulo, sinopse, anoLancamento, nota, idadeIndicativa, diretor_id, status) values(
                                                                                                'Interestelar',
                                                                                                'Um grupo de exploradores espaciais viaja através de um buraco de minhoca em busca de um novo lar para a humanidade, que está à beira da extinção na Terra.',
                                                                                                2014, 9.3, 12, 3, 'FORA_DE_CARTAZ'
                                                                                            );

insert into filme_genero (filme_id, genero_id) values (1, 1), (1, 6), (1, 5);
insert into filme_genero (filme_id, genero_id) values (2, 1), (2, 3), (2, 6);
insert into filme_genero (filme_id, genero_id) values (3, 2), (3, 4);
insert into filme_genero (filme_id, genero_id) values (4, 2), (4, 5);
insert into filme_genero (filme_id, genero_id) values (5, 6), (5, 2);
insert into filme_genero (filme_id, genero_id) values (6, 1), (6, 6), (6, 5);