CREATE TABLE if not exists tb_Email (
    idEmail INT AUTO_INCREMENT PRIMARY KEY,
    endEmail VARCHAR(50) NOT NULL
);
CREATE TABLE if not exists tb_Endereco (
    idEndereco INT AUTO_INCREMENT PRIMARY KEY,
    rua VARCHAR(75) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    bairro VARCHAR(30) NOT NULL,
    cidade VARCHAR(30) NOT NULL,
    numeroCasa int NOT NULL
);
CREATE TABLE if not exists tb_Telefone (
    idTelefone INT AUTO_INCREMENT PRIMARY KEY,
    Telefone VARCHAR(50) NOT NULL
);
CREATE TABLE if not exists tb_Cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nomeCliente VARCHAR(50) NOT NULL,
    cpf VARCHAR(20) NOT NULL,
    fk_Email int NOT NULL,
    fk_Endereco int NOT NULL,
    fk_Telefone int NOT NULL,
    foreign key (fk_Email) references tb_Email (idEmail),
    foreign key (fk_Endereco) references tb_Endereco (idEndereco),
    foreign key (fk_Telefone) references tb_Telefone (idTelefone)
);

CREATE TABLE if not exists tb_Categoria (
    idCategoria INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);
CREATE TABLE if not exists tb_Marca (
    idMarca INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);
CREATE TABLE if not exists tb_Produto (
    idMarca INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    custo decimal(10,2) NOT NULL,
    Valor decimal(10,2) NOT NULL,
    observacao text default null,
    fk_categoria int,
    fk_marca int,
    foreign key (fk_categoria) references tb_Categoria (idCategoria ),
    foreign key (fk_marca) references tb_Marca (idMarca)
);

CREATE TABLE if not exists tb_Estoque (
    idEstoque INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    quantidade int NOT NULLl,
    fk_produto int,
    foreign key (fk_produto) references tb_Produto (idProduto)
);

CREATE TABLE if not exists tb_Servico (
    idServico INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricaoServico text default null
    Valor decimal(10,2) NOT NULL
);

CREATE TABLE if not exists tb_Venda (
    idVenda INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    quantidade int NOT NULLl,
    fk_produto int,
    foreign key (fk_produto) references tb_Produto (idProduto)
);




