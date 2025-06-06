-- TABELA DE CLIENTE (DEVE VIR PRIMEIRO POR SER REFERENCIADA)
CREATE TABLE IF NOT EXISTS TB_CLIENTE (
    ID_CLIENTE INT PRIMARY KEY AUTO_INCREMENT,
    NOME_CLIENTE VARCHAR(50) DEFAULT NULL,
    CPF VARCHAR(20) DEFAULT NULL
);

-- TABELA DE EMAIL
CREATE TABLE IF NOT EXISTS TB_EMAIL (
    ID_EMAIL INT PRIMARY KEY AUTO_INCREMENT,
    END_EMAIL VARCHAR(50) DEFAULT NULL,
    FK_CLIENTE INT NOT NULL,
    FOREIGN KEY (FK_CLIENTE) REFERENCES TB_CLIENTE(ID_CLIENTE)
);

-- TABELA DE ENDERECO
CREATE TABLE IF NOT EXISTS TB_ENDERECO (
    ID_ENDERECO INT PRIMARY KEY AUTO_INCREMENT,
    RUA VARCHAR(75) DEFAULT NULL,
    CEP VARCHAR(9) DEFAULT NULL,
    BAIRRO VARCHAR(30) DEFAULT NULL,
    CIDADE VARCHAR(30) DEFAULT NULL,
    NUMERO_CASA INT NOT NULL,
    FK_CLIENTE INT NOT NULL,
    FOREIGN KEY (FK_CLIENTE) REFERENCES TB_CLIENTE(ID_CLIENTE)
);

-- TABELA DE TELEFONE
CREATE TABLE IF NOT EXISTS TB_TELEFONE (
    ID_TELEFONE INT PRIMARY KEY AUTO_INCREMENT,
    TELEFONE VARCHAR(20) DEFAULT NULL,
    FK_CLIENTE INT NOT NULL,
    FOREIGN KEY (FK_CLIENTE) REFERENCES TB_CLIENTE(ID_CLIENTE)
);

-- TABELA DE CATEGORIA
CREATE TABLE IF NOT EXISTS TB_CATEGORIA (
    ID_CATEGORIA INT PRIMARY KEY AUTO_INCREMENT,
    NOME VARCHAR(50) DEFAULT NULL
);

-- TABELA DE MARCA
CREATE TABLE IF NOT EXISTS TB_MARCA (
    ID_MARCA INT PRIMARY KEY AUTO_INCREMENT,
    NOME VARCHAR(50) DEFAULT NULL
);

-- TABELA DE PRODUTO
CREATE TABLE IF NOT EXISTS TB_PRODUTO (
    ID_PRODUTO INT PRIMARY KEY AUTO_INCREMENT,
    NOME VARCHAR(50) NOT NULL,
    CUSTO DECIMAL(10,2) NOT NULL,
    VALOR DECIMAL(10,2) NOT NULL,
    QUANTIDADE INT NOT NULL,
    DESCRICAO TEXT DEFAULT NULL,
    FK_CATEGORIA INT NOT NULL,
    FK_MARCA INT NOT NULL,
    FOREIGN KEY (FK_CATEGORIA) REFERENCES TB_CATEGORIA(ID_CATEGORIA),
    FOREIGN KEY (FK_MARCA) REFERENCES TB_MARCA(ID_MARCA)
);

-- TABELA DE ESTOQUE
CREATE TABLE IF NOT EXISTS TB_ESTOQUE (
    ID_ESTOQUE INT PRIMARY KEY AUTO_INCREMENT,
    QUANTIDADE INT NOT NULL,
    FK_PRODUTO INT NOT NULL,
    FOREIGN KEY (FK_PRODUTO) REFERENCES TB_PRODUTO(ID_PRODUTO)
);

-- TABELA DE SERVICO
CREATE TABLE IF NOT EXISTS TB_SERVICO (
    ID_SERVICO INT PRIMARY KEY AUTO_INCREMENT,
    NOME VARCHAR(75) NOT NULL,
    DESCRICAO_SERVICO TEXT DEFAULT NULL,
    VALOR DECIMAL(10,2) NOT NULL
);

-- TABELA DE VENDA
CREATE TABLE IF NOT EXISTS TB_VENDA (
    ID_VENDA INT PRIMARY KEY AUTO_INCREMENT,
    QUANTIDADE INT NOT NULL,
    DATA_INICIO DATE NOT NULL,
    HORA_INICIO TIME NOT NULL,  -- ALTEREI DE DATE PARA TIME
    VALOR DECIMAL(10,2) NOT NULL,
    LUCRO DECIMAL(10,2) NOT NULL,
    FK_PRODUTO INT NOT NULL,
    FK_CLIENTE INT NOT NULL,
    FOREIGN KEY (FK_PRODUTO) REFERENCES TB_PRODUTO(ID_PRODUTO),
    FOREIGN KEY (FK_CLIENTE) REFERENCES TB_CLIENTE(ID_CLIENTE)
);

-- TABELA DE ORDEM DE SERVICO (OS)
CREATE TABLE IF NOT EXISTS TB_OS (
    ID_OS INT PRIMARY KEY AUTO_INCREMENT,
    DATA_INICIO DATE,
    HORA_INICIO TIME,
    DATA_FIM DATE,
    HORA_FIM TIME,
    QUANTIDADE INT NOT NULL,
    VALOR DECIMAL(10,2) NOT NULL,
    LUCRO DECIMAL(10,2) NOT NULL,
    STATUS_OS VARCHAR(15) DEFAULT NULL,
    FK_SERVICO INT NOT NULL,
    FK_PRODUTO INT NOT NULL,
    FK_CLIENTE INT NOT NULL,
    FOREIGN KEY (FK_SERVICO) REFERENCES TB_SERVICO(ID_SERVICO),
    FOREIGN KEY (FK_PRODUTO) REFERENCES TB_PRODUTO(ID_PRODUTO),
    FOREIGN KEY (FK_CLIENTE) REFERENCES TB_CLIENTE(ID_CLIENTE)
);