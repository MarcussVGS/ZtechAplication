

INSERT INTO TB_CLIENTE (NOME_CLIENTE, CPF) VALUES 
('JOÃO SILVA', '123.456.789-01'),
('MARIA OLIVEIRA', '987.654.321-09'),
('CARLOS SOUZA', '456.789.123-45'),
('ANA SANTOS', '321.654.987-09'),
('PEDRO COSTA', '789.123.456-78');

INSERT INTO TB_EMAIL (END_EMAIL, FK_CLIENTE) VALUES 
('joao.silva@gmail.com', 1), 
('maria.oliveira@hotmail.com', 2), 
('carlos.souza@yahoo.com', 3), 
('ana.santos@outlook.com', 4), 
('pedro.costa@gmail.com', 5);

-- INSERTS PARA TB_ENDERECO
INSERT INTO TB_ENDERECO (RUA, CEP, BAIRRO, CIDADE, NUMERO_CASA, FK_CLIENTE) VALUES 
('Rua das Flores', '01234-567', 'Centro', 'São Paulo', 100, 1),
('Avenida Brasil', '98765-432', 'Jardins', 'Rio de Janeiro', 200, 2),
('Rua das Palmeiras', '45678-901', 'Vila Nova', 'Belo Horizonte', 300, 3),
('Alameda Santos', '23456-789', 'Paraíso', 'São Paulo', 400, 4),
('Rua do Comércio', '34567-890', 'Centro', 'Curitiba', 500, 5);

-- INSERTS PARA TB_TELEFONE
INSERT INTO TB_TELEFONE (TELEFONE, FK_CLIENTE) VALUES 
('(11) 99999-1111', 1),
('(21) 98888-2222', 2),
('(31) 97777-3333', 3),
('(11) 96666-4444', 4),
('(41) 95555-5555', 5);


-- INSERTS PARA TB_CATEGORIA
INSERT INTO TB_CATEGORIA (NOME) VALUES 
('ELETRÔNICOS'),
('MÓVEIS'),
('INFORMÁTICA'),
('CELULARES'),
('GAMES');

-- INSERTS PARA TB_MARCA
INSERT INTO TB_MARCA (NOME) VALUES 
('SAMSUNG'),
('APPLE'),
('SONY'),
('LG'),
('DELL');

-- INSERTS PARA TB_PRODUTO
INSERT INTO TB_PRODUTO (NOME, CUSTO, VALOR, QUANTIDADE, DESCRICAO, FK_CATEGORIA, FK_MARCA) VALUES 
('Smartphone Galaxy S20', 2500.00, 3500.00, 8, 'Smartphone top de linha', 4, 1),
('Notebook Inspiron 15', 3000.00, 4500.00, 2, 'Notebook para trabalho', 3, 5),
('TV OLED 55"', 3500.00, 5000.00, 1, 'TV com melhor imagem do mercado', 1, 4),
('PlayStation 5', 3500.00, 4500.00, 3, 'Console de última geração', 5, 3),
('iPhone 13', 4000.00, 5500.00, 5, 'Melhor smartphone da Apple', 4, 2);

-- INSERTS PARA TB_ESTOQUE
INSERT INTO TB_ESTOQUE (QUANTIDADE, FK_PRODUTO) VALUES 
(50, 1),
(30, 2),
(20, 3),
(15, 4),
(10, 5);

-- INSERTS PARA TB_SERVICO
INSERT INTO TB_SERVICO (NOME, DESCRICAO_SERVICO, VALOR) VALUES 
('Manutenção Preventiva', 'Limpeza e verificação geral', 150.00),
('Troca de Tela', 'Substituição de tela quebrada', 300.00),
('Formatação', 'Instalação de SO e drivers', 120.00),
('Upgrade de Memória', 'Instalação de memória RAM', 200.00),
('Recuperação de Dados', 'Recuperação de arquivos perdidos', 350.00);

-- INSERTS PARA TB_VENDA
INSERT INTO TB_VENDA (QUANTIDADE, DATA_INICIO, HORA_INICIO, VALOR, LUCRO, FK_PRODUTO, FK_CLIENTE) VALUES 
(1, '2023-01-10', '10:30:00', 3500.00, 1000.00, 1, 1),
(2, '2023-01-15', '14:15:00', 9000.00, 3000.00, 2, 2),
(1, '2023-02-05', '09:45:00', 5000.00, 1500.00, 3, 3),
(1, '2023-02-20', '16:20:00', 4500.00, 1000.00, 4, 4),
(1, '2023-03-01', '11:10:00', 5500.00, 1500.00, 5, 5);

-- INSERTS PARA TB_OS
INSERT INTO TB_OS (DATA_INICIO, HORA_INICIO, DATA_FIM, HORA_FIM, QUANTIDADE, VALOR, LUCRO, STATUS_OS, FK_SERVICO, FK_PRODUTO, FK_CLIENTE) VALUES 
('2023-01-05', '09:00:00', '2023-01-05', '12:00:00', 1, 150.00, 50.00, 'Registrada', 1, 1, 1),
('2023-01-12', '10:00:00', '2023-01-12', '13:30:00', 1, 300.00, 100.00, 'Em andamento', 2, 2, 2),
('2023-02-01', '14:00:00', '2023-02-01', '15:30:00', 1, 120.00, 40.00, 'Concluido', 3, 3, 3),
('2023-02-10', '11:00:00', '2023-02-10', '12:30:00', 1, 200.00, 80.00, 'Cliente Ausente', 4, 4, 4),
('2023-03-01', '13:00:00', '2023-03-01', '16:00:00', 1, 350.00, 150.00, 'Cancelado', 5, 5, 5);
