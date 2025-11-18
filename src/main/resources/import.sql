-- Biografias dos Treinadores
INSERT INTO BiografiaTreinador (id, textoCompleto, resumo, premiosRecebidos) VALUES
(1, 'John G. Avildsen foi um diretor de cinema americano. Ele ganhou o Oscar de Melhor Diretor por "Rocky", um dos filmes mais icônicos sobre boxe. Seu trabalho é conhecido por inspirar histórias de superação.', 'Diretor de "Rocky", vencedor do Oscar.', 'Oscar de Melhor Diretor'),
(2, 'Martin Scorsese é um dos diretores mais influentes da história do cinema. Dirigiu "Touro Indomável", um filme biográfico sobre o boxeador Jake LaMotta, aclamado pela crítica por sua cinematografia e realismo brutal.', 'Diretor de "Touro Indomável" e mestre do cinema moderno.', 'Oscar, Palma de Ouro, Leão de Ouro'),
(3, 'Clint Eastwood é um ator e diretor lendário. Ele dirigiu e atuou em "Menina de Ouro", um drama comovente que ganhou o Oscar de Melhor Filme e Melhor Diretor. Seus filmes frequentemente exploram temas de redenção e moralidade.', 'Diretor e ator de "Menina de Ouro", vencedor de múltiplos Oscars.', 'Oscar de Melhor Diretor, Oscar de Melhor Filme'),
(4, 'Ryan Coogler é um diretor e roteirista americano que revitalizou a franquia "Rocky" com "Creed". Seus filmes são conhecidos por sua relevância cultural e narrativas poderosas.', 'Diretor de "Creed", uma nova voz no cinema.', 'Prêmios em festivais de cinema independentes'),
(5, 'David O. Russell é um diretor conhecido por seus filmes sobre dramas familiares e personagens complexos. Ele dirigiu "O Vencedor", um filme biográfico sobre o boxeador Micky Ward, que recebeu aclamação da crítica e vários prêmios.', 'Diretor de "O Vencedor", aclamado pela crítica.', 'Indicações ao Oscar');
alter sequence biografiaTreinador_seq restart with 6;

-- Treinadores
INSERT INTO Treinador (id, nome, nascimento, nacionalidade, biografia_id) VALUES
(1, 'John G. Avildsen', '1935-12-21', 'Americana', 1),
(2, 'Martin Scorsese', '1942-11-17', 'Americana', 2),
(3, 'Clint Eastwood', '1930-05-31', 'Americana', 3),
(4, 'Ryan Coogler', '1986-05-23', 'Americana', 4),
(5, 'David O. Russell', '1958-08-20', 'Americana', 5);
alter sequence treinador_seq restart with 6;

-- Categorias de Peso
INSERT INTO CategoriaDePeso (id, nome, descricao) VALUES
(1, 'Peso Pesado', 'Categoria para lutadores com mais de 91 kg.'),
(2, 'Peso Médio', 'Categoria para lutadores entre 72.5 e 76.2 kg.'),
(3, 'Peso Leve', 'Categoria para lutadores entre 60 e 61.2 kg.'),
(4, 'Peso Pena', 'Categoria para lutadores entre 55.3 e 57.2 kg.');
alter sequence categoriaDePeso_seq restart with 5;

-- Lutadores
INSERT INTO Lutador (id, nome, historia, anoNascimento, ranking, vitorias, statusCarreira, treinador_id) VALUES
(1, 'Rocky Balboa', 'Um boxeador de bairro da Filadélfia que se torna uma lenda.', 1945, 9.5, 57, 'APOSENTADO', 1),
(2, 'Jake LaMotta', 'Um campeão de peso médio cuja raiva e ciúmes o levaram à autodestruição.', 1922, 9.2, 83, 'APOSENTADO', 2),
(3, 'Maggie Fitzgerald', 'Uma garçonete que se torna uma lutadora profissional com a ajuda de um treinador relutante.', 1973, 8.8, 21, 'INATIVO', 3),
(4, 'Adonis Creed', 'Filho do campeão Apollo Creed, que busca seguir os passos de seu pai no mundo do boxe.', 1986, 9.0, 25, 'ATIVO', 4),
(5, 'Micky Ward', 'Um boxeador de Lowell, Massachusetts, que supera as adversidades para se tornar um campeão mundial.', 1965, 8.5, 38, 'APOSENTADO', 5);
alter sequence lutador_seq restart with 6;


-- Relacionamento Lutador-CategoriaDePeso
INSERT INTO lutador_categoriadepeso (lutador_id, categoriadepeso_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 1),
(5, 3);

-- Chave de API
INSERT INTO ApiKey (id, description) VALUES ('minha-chave-secreta', 'Chave de acesso principal');