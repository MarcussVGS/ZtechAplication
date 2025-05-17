CREATE TABLE IF NOT EXISTS tb_Cliente (
    idCliente INT PRIMARY KEY AUTO_INCREMENT,
    nome_Cliente VARCHAR(50) default NULL,
    cpf VARCHAR(20) default NULL
);

CREATE TABLE IF NOT EXISTS tb_Email (
    idEmail INT PRIMARY KEY AUTO_INCREMENT,
    end_Email VARCHAR(50) default NULL,
	fk_Cliente INT NOT NULL,
	FOREIGN KEY (fk_cliente) REFERENCES tb_Cliente (idCliente)
    );

CREATE TABLE IF NOT EXISTS tb_Endereco (
    idEndereco INT PRIMARY KEY AUTO_INCREMENT,
    rua VARCHAR(75) default NULL,
    cep VARCHAR(9) default NULL,
    bairro VARCHAR(30) default NULL,
    cidade VARCHAR(30) default NULL,
    numero_Casa INT NOT NULL,
    fk_Cliente INT NOT NULL,
    FOREIGN KEY (fk_cliente) REFERENCES tb_Cliente (idCliente)
);

CREATE TABLE IF NOT EXISTS tb_Telefone (
    idTelefone INT PRIMARY KEY AUTO_INCREMENT,
    telefone VARCHAR(20) default NULL,
    fk_Cliente INT NOT NULL,
     FOREIGN KEY (fk_cliente) REFERENCES tb_Cliente (idCliente)
);

CREATE TABLE IF NOT EXISTS tb_Categoria (
    idCategoria INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) default NULL
);

CREATE TABLE IF NOT EXISTS tb_Marca (
    idMarca INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) default NULL
);

CREATE TABLE IF NOT EXISTS tb_Produto (
    idProduto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    custo DECIMAL(10,2) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    descricao TEXT DEFAULT NULL,
    fk_Categoria INT NOT NULL,
    fk_Marca INT NOT NULL,
    FOREIGN KEY (fk_Categoria) REFERENCES tb_Categoria (idCategoria),
    FOREIGN KEY (fk_Marca) REFERENCES tb_Marca (idMarca)
);

CREATE TABLE IF NOT EXISTS tb_Estoque (
    idEstoque INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    quantidade INT NOT NULL,
    fk_Produto INT NOT NULL,
    FOREIGN KEY (fk_produto) REFERENCES tb_Produto (idProduto)
);

CREATE TABLE IF NOT EXISTS tb_Servico (
    idServico INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricao_Servico TEXT DEFAULT NULL,
    valor DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_Venda (
    idVenda INT AUTO_INCREMENT PRIMARY KEY,
    quantidade INT NOT NULL,
    data_Inicio DATE NOT NULL,
    hora_Inicio DATE NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    lucro DECIMAL(10,2) NOT NULL,
    fk_Produto INT NOT NULL,
    fk_Cliente INT NOT NULL,
    FOREIGN KEY (fk_produto) REFERENCES tb_Produto (idProduto),
    FOREIGN KEY (fk_cliente) REFERENCES tb_Cliente (idCliente)
);

CREATE TABLE IF NOT EXISTS tb_OS (
	idOS INT AUTO_INCREMENT PRIMARY KEY,
	data_Inicio DATE,
	hora_Inicio TIME,
	data_Fim DATE,
	hora_Fim TIME,
	valor DECIMAL(10,2) NOT NULL,
	lucro DECIMAL(10,2) NOT NULL,
	fk_Servico INT NOT NULL,
	fk_Estoque INT NOT NULL,
	fk_Cliente INT NOT NULL,
	FOREIGN KEY (fk_Servico) REFERENCES tb_Servico (idServico),
	FOREIGN KEY (fk_Estoque) REFERENCES tb_Estoque (idEstoque),
	FOREIGN KEY (fk_Cliente) REFERENCES tb_Cliente (idCliente)
);


INSERT INTO tb_Cliente (nome_Cliente, cpf) VALUES 
('João Silva', '123.456.789-01'),
('Maria Oliveira', '987.654.321-09'),
('Carlos Souza', '456.789.123-45'),
('Ana Santos', '321.654.987-09'),
('Pedro Costa', '789.123.456-78');

INSERT INTO tb_Email (end_Email, fk_cliente) VALUES 
('joao.silva@gmail.com', 1), 
('maria.oliveira@hotmail.com', 2), 
('carlos.souza@yahoo.com', 3), 
('ana.santos@outlook.com', 4), 
('pedro.costa@gmail.com', 5);


--ss
