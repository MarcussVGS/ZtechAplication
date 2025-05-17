CREATE TABLE IF NOT EXISTS tb_Email (
    idEmail INT PRIMARY KEY AUTO_INCREMENT,
    endEmail VARCHAR(50) default NULL
);

CREATE TABLE IF NOT EXISTS tb_Endereco (
    idEndereco INT PRIMARY KEY AUTO_INCREMENT,
    rua VARCHAR(75) default NULL,
    cep VARCHAR(9) default NULL,
    bairro VARCHAR(30) default NULL,
    cidade VARCHAR(30) default NULL,
    numeroCasa INT NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_Telefone (
    idTelefone INT PRIMARY KEY AUTO_INCREMENT,
    telefone VARCHAR(20) default NULL
);

CREATE TABLE IF NOT EXISTS tb_Cliente (
    idCliente INT PRIMARY KEY AUTO_INCREMENT,
    nomeCliente VARCHAR(50) default NULL,
    cpf VARCHAR(20) default NULL,
    fk_Email INT NOT NULL,
    fk_Endereco INT NOT NULL,
    fk_Telefone INT NOT NULL,
    FOREIGN KEY (fk_Email) REFERENCES tb_Email (idEmail),
    FOREIGN KEY (fk_Endereco) REFERENCES tb_Endereco (idEndereco),
    FOREIGN KEY (fk_Telefone) REFERENCES tb_Telefone (idTelefone)
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
    fk_produto INT NOT NULL,
    FOREIGN KEY (fk_produto) REFERENCES tb_Produto (idProduto)
);

CREATE TABLE IF NOT EXISTS tb_Servico (
    idServico INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricaoServico TEXT DEFAULT NULL,
    valor DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_Venda (
    idVenda INT AUTO_INCREMENT PRIMARY KEY,
    quantidade INT NOT NULL,
    dataInicio DATE NOT NULL,
    horaInicio DATE NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    lucro DECIMAL(10,2) NOT NULL,
    fk_produto INT NOT NULL,
    fk_cliente INT NOT NULL,
    FOREIGN KEY (fk_produto) REFERENCES tb_Produto (idProduto),
    FOREIGN KEY (fk_cliente) REFERENCES tb_Cliente (idCliente)
);

CREATE TABLE IF NOT EXISTS tb_OS (
	idOS INT AUTO_INCREMENT PRIMARY KEY,
	dataInicio DATE,
	horaInicio TIME,
	dataFim DATE,
	horaFim TIME,
	valor DECIMAL(10,2) NOT NULL,
	lucro DECIMAL(10,2) NOT NULL,
	fk_Servico INT NOT NULL,
	fk_Estoque INT NOT NULL,
	fk_Cliente INT NOT NULL,
	FOREIGN KEY (fk_Servico) REFERENCES tb_Servico (idServico),
	FOREIGN KEY (fk_Estoque) REFERENCES tb_Estoque (idEstoque),
	FOREIGN KEY (fk_Cliente) REFERENCES tb_Cliente (idCliente)
);



-- safadas sss 


