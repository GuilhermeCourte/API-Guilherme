-- Diretores
INSERT INTO BiografiaDiretor (id, textoCompleto, resumo, premiosRecebidos) VALUES
(1, 'Hayao Miyazaki é um dos mais célebres cineastas de animação do Japão. Cofundador do Studio Ghibli, ele dirigiu vários filmes aclamados internacionalmente, conhecidos por seus temas recorrentes de humanismo, ambientalismo e pacifismo. Suas obras frequentemente apresentam protagonistas femininas fortes e exploram a relação entre a humanidade e a natureza.', 'Cofundador do Studio Ghibli e aclamado diretor de animação.', 'Oscar, Urso de Ouro, Leão de Ouro'),
(2, 'Isao Takahata foi um diretor, produtor e roteirista japonês. Cofundador do Studio Ghibli, ele era conhecido por seu estilo realista e dramas comoventes que exploravam a sociedade e a história japonesa. Seu trabalho é marcado por uma abordagem experimental e um profundo senso de humanidade.', 'Cofundador do Studio Ghibli, conhecido por seus dramas realistas.', 'Prêmio do Festival de Annecy, Ordem das Artes e das Letras'),
(3, 'Gorō Miyazaki é um diretor de animação japonês e filho de Hayao Miyazaki. Ele dirigiu vários filmes no Studio Ghibli, trazendo uma nova perspectiva para o estúdio. Embora seu trabalho seja influenciado por seu pai, ele desenvolveu um estilo próprio, explorando temas de identidade e pertencimento.', 'Diretor de animação do Studio Ghibli e filho de Hayao Miyazaki.', 'Prêmios da Academia Japonesa'),
(4, 'Hiromasa Yonebayashi é um animador e diretor japonês que trabalhou no Studio Ghibli por muitos anos. Seus filmes são conhecidos por sua beleza visual e narrativas delicadas. Ele é o diretor mais jovem a dirigir um filme para o estúdio.', 'Ex-diretor do Studio Ghibli, conhecido por sua animação detalhada.', 'Indicações a prêmios internacionais'),
(5, 'Yoshifumi Kondō foi um diretor de animação japonês que teve uma carreira promissora no Studio Ghibli. Ele dirigiu apenas um filme, "Sussurros do Coração", antes de seu falecimento prematuro. Seu trabalho é lembrado por sua sensibilidade e atenção aos detalhes da vida cotidiana.', 'Diretor de "Sussurros do Coração", uma promessa do Studio Ghibli.', 'N/A');
alter sequence biografiaDiretor_seq restart with 6;

INSERT INTO Diretor (id, nome, nascimento, nacionalidade, biografia_id) VALUES
(1, 'Hayao Miyazaki', '1941-01-05', 'Japonesa', 1),
(2, 'Isao Takahata', '1935-10-29', 'Japonesa', 2),
(3, 'Gorō Miyazaki', '1967-01-21', 'Japonesa', 3),
(4, 'Hiromasa Yonebayashi', '1973-07-10', 'Japonesa', 4),
(5, 'Yoshifumi Kondō', '1950-03-31', 'Japonesa', 5);
alter sequence diretor_seq restart with 6;

-- Gêneros
INSERT INTO Genero (id, nome, descricao) VALUES
(1, 'Fantasia', 'Filmes com elementos mágicos e mundos fantásticos'),
(2, 'Aventura', 'Jornadas épicas, descobertas e desafios pessoais'),
(3, 'Família', 'Filmes adequados para todas as idades'),
(4, 'Drama', 'Narrativas emocionais profundas sobre a condição humana'),
(5, 'Romance', 'Histórias centradas no amor e nos relacionamentos'),
(6, 'Guerra', 'Filmes que retratam conflitos armados e suas consequências');
alter sequence genero_seq restart with 7;


-- Filmes
INSERT INTO Filme (id, titulo, sinopse, anoLancamento, nota, idadeIndicativa, diretor_id, status) VALUES
(1, 'A Viagem de Chihiro', 'Chihiro, uma menina de 10 anos, se aventura em um mundo mágico e misterioso governado por deuses, bruxas e espíritos.', 2001, 9.3, 0, 1, 'FORA_DE_CARTAZ'),
(2, 'Meu Vizinho Totoro', 'Duas irmãs se mudam para o campo para ficar perto de sua mãe doente e fazem amizade com os espíritos da floresta que vivem nas proximidades.', 1988, 8.2, 0, 1, 'FORA_DE_CARTAZ'),
(3, 'O Castelo Animado', 'Uma jovem é amaldiçoada por uma bruxa e se transforma em uma mulher idosa. Para quebrar o feitiço, ela busca a ajuda de um mago excêntrico que vive em um castelo ambulante.', 2004, 8.7, 0, 1, 'FORA_DE_CARTAZ'),
(4, 'Princesa Mononoke', 'Ashitaka, um príncipe de uma tribo Emishi, se envolve em um conflito entre os deuses de uma floresta e os humanos que a exploram.', 1997, 8.4, 12, 1, 'FORA_DE_CARTAZ'),
(5, 'O Túmulo dos Vagalumes', 'Dois irmãos lutam para sobreviver no Japão durante os últimos meses da Segunda Guerra Mundial.', 1988, 8.5, 12, 2, 'FORA_DE_CARTAZ');
alter sequence filme_seq restart with 6;

-- Relacionamento Filme-Gênero
INSERT INTO filme_genero (filme_id, genero_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4),
(2, 1), (2, 3),
(3, 1), (3, 2), (3, 5),
(4, 1), (4, 2), (4, 6),
(5, 4), (5, 6);
