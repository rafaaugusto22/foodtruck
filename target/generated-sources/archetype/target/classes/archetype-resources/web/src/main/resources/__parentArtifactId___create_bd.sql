CREATE SEQUENCE SEQ_nuEmpresa;
 
CREATE TABLE Empresa (
    nuEmpresa     INTEGER NOT NULL,
      CNPJ          CHAR(14) NOT NULL,
      NomeFantasia  VARCHAR(256) NOT NULL,
      RazaoSocial   VARCHAR(256) NOT NULL,
      Ativo         BOOLEAN DEFAULT TRUE NOT NULL,
 
    CONSTRAINT Empresa_PK PRIMARY KEY (nuEmpresa)
);
 
ALTER TABLE Empresa ADD CONSTRAINT Empresa_UQ_01 UNIQUE (CNPJ);
ALTER TABLE Empresa ADD CONSTRAINT Empresa_UQ_02 UNIQUE (NomeFantasia);
 
CREATE INDEX Empresa_IX_01 ON Empresa(RazaoSocial);
 
CREATE SEQUENCE SEQ_nuUnidade;
 
CREATE TABLE Unidade (
    nuEmpresa     INTEGER NOT NULL,
    nuUnidade     INTEGER NOT NULL,
      Nome          VARCHAR(256) NOT NULL,
      Sigla         VARCHAR(16) NOT NULL,
      Logradouro    VARCHAR(128),
      Complemento   VARCHAR(32),
      Bairro        VARCHAR(64),
      Localidade    VARCHAR(128),
      UF            CHAR(2),
      CEP           CHAR(8),
      Ativo         BOOLEAN DEFAULT TRUE NOT NULL,
 
    CONSTRAINT Unidade_PK PRIMARY KEY (nuUnidade)
);
 
ALTER TABLE Unidade
     ADD CONSTRAINT Unidade_FK_01 FOREIGN KEY (nuEmpresa)
          REFERENCES Empresa (nuEmpresa);
CREATE INDEX Unidade_IXFK_01 ON Unidade(nuEmpresa);
 
ALTER TABLE Unidade ADD CONSTRAINT Unidade_UQ_01 UNIQUE (nuEmpresa, Nome);
ALTER TABLE Unidade ADD CONSTRAINT Unidade_UQ_02 UNIQUE (nuEmpresa, Sigla);
 
CREATE INDEX Unidade_IX_01 ON Unidade(nuEmpresa, UF);
 
CREATE SEQUENCE SEQ_nuFuncao;
 
CREATE TABLE Funcao (
    nuFuncao      INTEGER NOT NULL,
      Nome          VARCHAR(256) NOT NULL,
      coFuncao      VARCHAR(16) NOT NULL,
      Ativo         BOOLEAN DEFAULT TRUE NOT NULL,
 
    CONSTRAINT Funcao_PK PRIMARY KEY (nuFuncao)
);
 
ALTER TABLE Funcao ADD CONSTRAINT Funcao_UQ_01 UNIQUE (Nome);
ALTER TABLE Funcao ADD CONSTRAINT Funcao_UQ_02 UNIQUE (coFuncao);

CREATE SEQUENCE SEQ_nuHabilidade;
 
CREATE TABLE Habilidade (
    nuHabilidade  INTEGER NOT NULL,
      Nome          VARCHAR(256) NOT NULL,
      coHabilidade  VARCHAR(16) NOT NULL,
      Ativo         BOOLEAN DEFAULT TRUE NOT NULL,
 
    CONSTRAINT Habilidade_PK PRIMARY KEY (nuHabilidade)
);
 
ALTER TABLE Habilidade ADD CONSTRAINT Habilidade_UQ_01 UNIQUE (Nome);
ALTER TABLE Habilidade ADD CONSTRAINT Habilidade_UQ_02 UNIQUE (coHabilidade);

CREATE SEQUENCE SEQ_nuEmpregado;
 
CREATE TABLE Empregado (
    nuEmpregado   INTEGER NOT NULL,
    nuUnidade     INTEGER NOT NULL,
	nuFuncao      INTEGER NOT NULL,
      Nome          VARCHAR(256) NOT NULL,
      DtNasc        DATE NOT NULL,
      CPF           CHAR(11) NOT NULL,
      Sexo          CHAR(1),
      EstadoCivil   CHAR(1),
      Logradouro    VARCHAR(128),
      Complemento   VARCHAR(32),
      Bairro        VARCHAR(64),
      Localidade    VARCHAR(128),
      UF            CHAR(2),
      CEP           CHAR(8),
      Ativo         BOOLEAN DEFAULT TRUE NOT NULL,
    CONSTRAINT Empregado_PK PRIMARY KEY (nuEmpregado)
);
 
ALTER TABLE Empregado
     ADD CONSTRAINT Empregado_FK_01 FOREIGN KEY (nuUnidade)
          REFERENCES Unidade (nuUnidade);
CREATE INDEX Empregado_IXFK_01 ON Empregado(nuUnidade);

ALTER TABLE Empregado
     ADD CONSTRAINT Empregado_FK_02 FOREIGN KEY (nuFuncao)
          REFERENCES Funcao (nuFuncao);
CREATE INDEX Empregado_IXFK_02 ON Empregado(nuFuncao);
 
ALTER TABLE Empregado ADD CONSTRAINT Empregado_UQ_01 UNIQUE (nuEmpregado, Nome, DtNasc);
ALTER TABLE Empregado ADD CONSTRAINT Empregado_UQ_02 UNIQUE (nuEmpregado,CPF);


CREATE TABLE EmpregadoHabilidade (
    nuEmpregado   INTEGER NOT NULL,
    nuHabilidade  INTEGER NOT NULL,
    CONSTRAINT EmpregadoHabilidade_PK PRIMARY KEY (nuEmpregado, nuHabilidade)
);
 
ALTER TABLE EmpregadoHabilidade
     ADD CONSTRAINT EmpregadoHabilidade_FK_01 FOREIGN KEY (nuEmpregado)
          REFERENCES Empregado (nuEmpregado);
CREATE INDEX EmpregadoHabilidade_IXFK_01 ON EmpregadoHabilidade(nuEmpregado);

ALTER TABLE EmpregadoHabilidade
     ADD CONSTRAINT EmpregadoHabilidade_FK_02 FOREIGN KEY (nuHabilidade)
          REFERENCES Habilidade (nuHabilidade);
CREATE INDEX EmpregadoHabilidade_IXFK_02 ON EmpregadoHabilidade(nuHabilidade);

CREATE SEQUENCE SEQ_nuDependente;
 
CREATE TABLE Dependente (
    nuDependente  INTEGER NOT NULL,
    nuEmpregado   INTEGER NOT NULL,
      Nome          VARCHAR(256) NOT NULL,
      DtNasc        DATE NOT NULL,
      CPF           CHAR(11) NOT NULL,
      Sexo          CHAR(1),
 
    CONSTRAINT Dependente_PK PRIMARY KEY (nuDependente)
);
 
ALTER TABLE Dependente
     ADD CONSTRAINT Dependente_FK_01 FOREIGN KEY (nuEmpregado)
          REFERENCES Empregado (nuEmpregado);
CREATE INDEX Dependente_IXFK_01 ON Dependente (nuEmpregado);
 
ALTER TABLE Dependente ADD CONSTRAINT Dependente_UQ_01 UNIQUE (nuEmpregado, Nome, DtNasc);