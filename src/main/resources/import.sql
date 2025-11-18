-- Biografias dos Diretores
INSERT INTO BiografiaDiretor (id, textoCompleto, resumo, premiosRecebidos) VALUES
(1, 'John G. Avildsen foi um diretor de cinema americano. Ele ganhou o Oscar de Melhor Diretor por "Rocky", um dos filmes mais icônicos sobre boxe. Seu trabalho é conhecido por inspirar histórias de superação.', 'Diretor de "Rocky", vencedor do Oscar.', 'Oscar de Melhor Diretor'),
(2, 'Martin Scorsese é um dos diretores mais influentes da história do cinema. Dirigiu "Touro Indomável", um filme biográfico sobre o boxeador Jake LaMotta, aclamado pela crítica por sua cinematografia e realismo brutal.', 'Diretor de "Touro Indomável" e mestre do cinema moderno.', 'Oscar, Palma de Ouro, Leão de Ouro'),
(3, 'Clint Eastwood é um ator e diretor lendário. Ele dirigiu e atuou em "Menina de Ouro", um drama comovente que ganhou o Oscar de Melhor Filme e Melhor Diretor. Seus filmes frequentemente exploram temas de redenção e moralidade.', 'Diretor e ator de "Menina de Ouro", vencedor de múltiplos Oscars.', 'Oscar de Melhor Diretor, Oscar de Melhor Filme'),
(4, 'Ryan Coogler é um diretor e roteirista americano que revitalizou a franquia "Rocky" com "Creed". Seus filmes são conhecidos por sua relevância cultural e narrativas poderosas.', 'Diretor de "Creed", uma nova voz no cinema.', 'Prêmios em festivais de cinema independentes'),
(5, 'David O. Russell é um diretor conhecido por seus filmes sobre dramas familiares e personagens complexos. Ele dirigiu "O Vencedor", um filme biográfico sobre o boxeador Micky Ward, que recebeu aclamação da crítica e vários prêmios.', 'Diretor de "O Vencedor", aclamado pela crítica.', 'Indicações ao Oscar');
alter sequence biografiaDiretor_seq restart with 6;

-- Diretores
INSERT INTO Diretor (id, nome, nascimento, nacionalidade, biografia_id) VALUES
(1, 'John G. Avildsen', '1935-12-21', 'Americana', 1),
(2, 'Martin Scorsese', '1942-11-17', 'Americana', 2),
(3, 'Clint Eastwood', '1930-05-31', 'Americana', 3),
(4, 'Ryan Coogler', '1986-05-23', 'Americana', 4),
(5, 'David O. Russell', '1958-08-20', 'Americana', 5);
alter sequence diretor_seq restart with 6;

-- Gêneros
INSERT INTO Genero (id, nome, descricao) VALUES
(1, 'Drama Esportivo', 'Filmes que usam o esporte como pano de fundo para explorar o drama humano.'),
(2, 'Biografia', 'Filmes que contam a história de vida de uma pessoa real.'),
(3, 'Ação', 'Filmes com sequências de ação intensas e emocionantes.'),
(4_ 'Drama', 'Narrativas emocionais profundas sobre a condição humana');
alter sequence genero_seq restart with 5;

-- Filmes
INSERT INTO Filme (id, titulo, sinopse, anoLancamento, nota, idadeIndicativa, diretor_id, status) VALUES
(1, 'Rocky: Um Lutador', 'Um boxeador de bairro da Filadélfia tem a chance única de lutar pelo título mundial de pesos pesados contra o campeão Apollo Creed.', 1976, 8.1, 12, 1, 'FORA_DE_CARTAZ'),
(2, 'Touro Indomável', 'A história de Jake LaMotta, um boxeador autodestrutivo que sobe ao topo, mas cuja raiva e ciúmes o levam à sua queda.', 1980, 8.2, 16, 2, 'FORA_DE_CARTAZ'),
(3, 'Menina de Ouro', 'Uma garçonete determinada busca a ajuda de um treinador de boxe amargo para se tornar uma profissional. Juntos, eles formam um vínculo que mudará suas vidas.', 2004, 8.1, 14, 3, 'FORA_DE_CARTAZ'),
(4, 'Creed: Nascido para Lutar', 'Adonis Johnson, filho do campeão Apollo Creed, viaja para a Filadélfia para ser treinado por Rocky Balboa, o antigo rival e amigo de seu pai.', 2015, 7.6, 12, 4, 'FORA_DE_CARTAZ'),
(5, 'O Vencedor', 'A história real do boxeador "Irish" Micky Ward e seu irmão mais velho, Dicky Eklund, que o ajuda a treinar para o título mundial de pesos leves.', 2010, 7.8, 14, 5, 'FORA_DE_CARTAZ');
alter sequence filme_seq restart with 6;


-- Relacionamento Filme-Gênero
INSERT INTO filme_genero (filme_id, genero_id) VALUES
(1, 1), (1, 4),
(2, 1), (2, 2),
(3, 1), (3, 4),
(4, 1), (4, 3),
(5, 1), (5, 2);